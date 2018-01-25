package com.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.OrderDetailEntity;
import com.app.entity.OrderHeadEntity;
import com.app.service.OrderService;

@Controller
public class SaleController {
	@Autowired
	private OrderService orderService;

	@ResponseBody
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	public String saveOrder(@RequestBody OrderHeadEntity order) {

		System.out.println("order entity:" + JSON.toJSONString(order));

		for (OrderDetailEntity ode : order.getOrderList()) {
			ode.setOrderHead(order);
		}

		orderService.save(order);

		System.out.println("start to test update head");
		// 测试保存
		order.setId(7);
		order.setTotalPay(10L);
		orderService.save(order);

		System.out.println("start to test update body");
		// 测试保存
		order.setId(7);
		order.getOrderList().get(0).setId(13L);
		order.getOrderList().get(0).setNum(-1);
		orderService.save(order);
		return "test";
	}

}
