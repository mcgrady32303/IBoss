package com.app.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.CustomerEntity;
import com.app.entity.ItemEntity;
import com.app.entity.OrderDetailEntity;
import com.app.entity.OrderHeadEntity;
import com.app.pojo.ItemDTO;
import com.app.pojo.OrderDTO;
import com.app.service.OrderService;

@Controller
public class SaleController {
	@Autowired
	private OrderService orderService;

	@ResponseBody
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	public String saveOrder(@RequestBody OrderDTO o) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		OrderHeadEntity order = new OrderHeadEntity();

		CustomerEntity customer = new CustomerEntity();
		customer.setId(o.getCustomer_id());

		System.out.println(JSON.toJSONString(o));

		try {
			order.setDate(formatter.parse(o.getDate()));
		} catch (ParseException e) {
			e.printStackTrace();
			return "日期格式错误";
		}
		order.setPayed(o.isPayed());
		order.setTotalPay(o.getTotalPrice());
//		order.setCustomer(customer);

		for (ItemDTO i : o.getOrderList()) {
			ItemEntity item = new ItemEntity();
			item.setId(i.getItem_id());
			OrderDetailEntity ode = new OrderDetailEntity();
//			ode.setItem(item);
			ode.setNum(i.getNum());
			ode.setPrice(i.getPrice());
			order.getOrderList().add(ode);
		}
		orderService.save(order);
		return "test";
	}

}
