package com.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

		testSave();

		testDelete();

		return "test";
	}

	@ResponseBody
	@RequestMapping(value = "/sale/listOrderByDate/{date}", method = RequestMethod.GET)
	public String listOrderByDate(@PathVariable String date) {
		System.out.println("date:" + date);
		return "result";
	}

	private void testSave() {

		OrderHeadEntity order = orderService.getOne(1L);
		System.out.println("start to test update head");
		// 测试保存
		order.setTotalPay(10000.38);
		orderService.save(order);

		System.out.println("start to test update body");
		// 测试保存
		OrderHeadEntity order2 = orderService.getOne(2L);
		order2.getOrderList().get(0).setNum(-1);
		order2.getOrderList().remove(1);// 外键关联实体无法通过save删除
		orderService.save(order2);
	}

	private void testDelete() {
		OrderHeadEntity order = orderService.getOne(3L);
		orderService.delete(order);
	}

}
