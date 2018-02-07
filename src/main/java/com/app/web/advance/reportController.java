package com.app.web.advance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.CustomerEntity;
import com.app.entity.ItemEntity;
import com.app.entity.OrderDetailEntity;
import com.app.entity.OrderHeadEntity;
import com.app.service.CustomerService;
import com.app.service.ItemService;
import com.app.service.OrderService;

@Controller
@RequestMapping("/report/")
public class reportController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<String> head = new ArrayList<String>(12);
	private List<String> end = new ArrayList<String>(12);

	private List<ItemEntity> items;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ItemService itemService;
	
	
	@ResponseBody
	@RequestMapping(value = "saleVolume", method = RequestMethod.GET)
	public String getSaleVolume() {
		return getSaleVolumeByYear("2018");
	}
	
	private String getSaleVolumeByYear(String year) {
		List<Double> saleVolume = new ArrayList<Double>(12);
		generateHeadAndEnd(year);
		for(int i = 0; i < 12; i++) {
			Double monthToltal = 0.0;
			List<OrderHeadEntity> orderList = listAllOrderByDateRange(head.get(0), end.get(i));
			for(OrderHeadEntity ohe : orderList) {
				monthToltal += ohe.getTotalPay();
			}
			saleVolume.add(i, monthToltal);
		}
		return JSON.toJSONString(saleVolume);
	}

	private List<OrderHeadEntity> listAllOrderByDateRange(String start,
			String end) {

		List<OrderHeadEntity> headList = orderService.findALLBetweenDate(start,
				end);

		if (!headList.isEmpty()) {
			for (OrderHeadEntity oneOrder : headList) { // 将customer名字查询出来,并赋值到订单body中
				assembleOrder(oneOrder);
			}
		}

		return headList;

	}

	private void assembleOrder(OrderHeadEntity order) {
		CustomerEntity ce = customerService.findOne(order.getCustomerId());
		if (ce != null) {
			order.setCustomerName(ce.getName());
		} else {
			order.setCustomerName("找不到用户名");
		}

		for (OrderDetailEntity ode : order.getOrderList()) { // 将item名字查询出来,并赋值到订单详情中
			ItemEntity ie = itemService.findOne(ode.getItemId());
			if (ie != null) {
				ode.setItemName(ie.getName());
			} else {
				ode.setItemName("找不到材料名");
			}

		}
	}

	private void generateHeadAndEnd(String year) {
		head.clear();
		end.clear();
		for (int i = 1; i <= 12; i++) {
			head.add(year + "-" + (i > 9 ? "" : "-0") + i + "-01");
			end.add(year + "-" + (i > 9 ? "" : "-0") + i + "31");
		}

	}

}
