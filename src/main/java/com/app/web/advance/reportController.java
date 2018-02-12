package com.app.web.advance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.CustomerEntity;
import com.app.entity.ItemEntity;
import com.app.entity.OrderDetailEntity;
import com.app.entity.OrderHeadEntity;
import com.app.pojo.Top10DebtDTO;
import com.app.service.CustomerService;
import com.app.service.ItemService;
import com.app.service.OrderService;

@Controller
@RequestMapping("/report/")
public class reportController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<String> head = new ArrayList<String>(12);
	private List<String> end = new ArrayList<String>(12);

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ItemService itemService;

	@ResponseBody
	@RequestMapping(value = "saleVolume/{year}", method = RequestMethod.GET)
	public String getSaleVolume(@PathVariable String year) {

		logger.info(year + "year sale data: "
				+ getSaleVolumeByYear(year));
		return getSaleVolumeByYear(year);
	}

	@ResponseBody
	@RequestMapping(value = "debtTop10/{year}", method = RequestMethod.GET)
	public String getDebtTop10(@PathVariable String year) {
		return getDebtTop10ByYear(year);
	}

	private String getDebtTop10ByYear(String year) {

		Map<Long, Double> debtMap = new HashMap<Long, Double>();
		boolean isLast = false;
		int startPage = 0;
		while (!isLast) {
			List<OrderHeadEntity> headList = new ArrayList<OrderHeadEntity>();
			isLast = listAllHasDebtOrderPageable(headList, startPage, 50, year);
			startPage++;
			for (OrderHeadEntity ohe : headList) {
				if (debtMap.containsKey(ohe.getCustomerId())) {
					Double oldVal = debtMap.get(ohe.getCustomerId());
					oldVal += ohe.getDebt();
					debtMap.put(ohe.getCustomerId(), oldVal);
				} else {
					debtMap.put(ohe.getCustomerId(), ohe.getDebt());
				}
			}
		}

		// 对map进行排序
		List<Map.Entry<Long, Double>> list = new ArrayList<Map.Entry<Long, Double>>(
				debtMap.entrySet());
		Collections.sort(list, new MapValueComparator());

		Top10DebtDTO top10 = new Top10DebtDTO();
		int i = 0;
		for (Map.Entry<Long, Double> mapping : list) {
			logger.info(mapping.getKey() + ":" + mapping.getValue());
			String name = getCustomerName(mapping.getKey());
			top10.addCustomerId(mapping.getKey());
			top10.addDebt(mapping.getValue());
			top10.addName(name);
			i++;
			if (i > 9)
				break;
		}

		logger.info(JSON.toJSONString(top10));
		return JSON.toJSONString(top10);
	}

	private String getCustomerName(Long key) {
		CustomerEntity ce = customerService.findOne(key);
		return ce != null ? ce.getName() : "没名字";
	}

	private boolean listAllHasDebtOrderPageable(List<OrderHeadEntity> headList,
			Integer page, Integer size, String year) {
		Page<OrderHeadEntity> orderList = orderService.findAllHasDebtByYear(
				page, size, year);
		for (OrderHeadEntity ohe : orderList.getContent()) {
			assembleOrder(ohe);
			headList.add(ohe);
		}

		logger.debug("size :" + orderList.getSize());
		logger.debug("number :" + orderList.getNumberOfElements());

		return size > orderList.getNumberOfElements();

	}

	private String getSaleVolumeByYear(String year) {
		List<Double> saleVolume = new ArrayList<Double>(12);
		generateHeadAndEnd(year);
		for (int i = 0; i < 12; i++) {
			Double monthToltal = 0.0;

			logger.info("start:" + head.get(i) + ", end: " + end.get(i));

			List<OrderHeadEntity> orderList = listAllOrderByDateRange(
					head.get(i), end.get(i));
			for (OrderHeadEntity ohe : orderList) {
				monthToltal += ohe.getTotalPay();
			}
			saleVolume.add(i, monthToltal);
			logger.info("第" + (i + 1) + "月的销量: " + monthToltal);
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
			head.add(year + "-" + (i > 9 ? "" : "0") + i + "-01");
			end.add(year + "-" + (i > 9 ? "" : "0") + i + "-31");
		}

	}

	class MapValueComparator implements Comparator<Map.Entry<Long, Double>> {

		@Override
		public int compare(Entry<Long, Double> me1, Entry<Long, Double> me2) {

			return me2.getValue().compareTo(me1.getValue());
		}
	}



}
