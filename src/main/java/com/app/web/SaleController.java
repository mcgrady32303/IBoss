package com.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.OrderHeadEntity;
import com.app.service.OrderService;

@Controller
public class SaleController {
	@Autowired
	private OrderService orderService;
	
	@ResponseBody
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	public String saveOrder(@RequestBody OrderHeadEntity order) {
		System.out.println(JSON.toJSONString(order));
		orderService.save(order);		
		return "test";		
	}

}
