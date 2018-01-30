package com.app.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.app.service.OrderDetailService;
import com.app.service.OrderService;

@Controller
public class SaleController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderDetailService orderDetailService;


	@ResponseBody
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	public String saveOrder(@RequestBody OrderHeadEntity order) {

		System.out.println("order entity:" + JSON.toJSONString(order));

		for (OrderDetailEntity ode : order.getOrderList()) {
			ode.setOrderHead(order);
		}

		orderService.save(order);

		return "test";
	}
	
	@ResponseBody
	@RequestMapping(value = "/sale/listOrderById/{id}", method = RequestMethod.GET)
	public String listOrderById(@PathVariable Long id) {
		
		OrderHeadEntity order = orderService.getOne(id);
		CustomerEntity ce = customerService.findOne(order.getCustomerId());
		if(ce != null) {
			order.setCustomerName(ce.getName());
		} else {
			order.setCustomerName("找不到用户名");
		}				
		
		for(OrderDetailEntity ode: order.getOrderList()) {  //将item名字查询出来,并赋值到订单详情中
			ItemEntity ie = itemService.findOne(ode.getItemId());
			ode.setItemName(ie.getName());
		}
		return JSON.toJSONString(order);
	}

	@ResponseBody
	@RequestMapping(value = "/sale/listOrderByDate/{date}", method = RequestMethod.GET)
	public String listOrderByDate(@PathVariable String date) {
		
		List<OrderHeadEntity> headList = orderService.findALLByDate(date);
		
		if(!headList.isEmpty()) {			
			for(OrderHeadEntity oneOrder : headList) {  //将customer名字查询出来,并赋值到订单body中	
				CustomerEntity ce = customerService.findOne(oneOrder.getCustomerId());
				if(ce != null) {
					oneOrder.setCustomerName(ce.getName());
				} else {
					oneOrder.setCustomerName("找不到用户名");
				}				
				
				for(OrderDetailEntity ode: oneOrder.getOrderList()) {  //将item名字查询出来,并赋值到订单详情中
					ItemEntity ie = itemService.findOne(ode.getItemId());
					ode.setItemName(ie.getName());
				}
			}

		} else {
			//date那天无订单
		}
		
		System.out.println(date+"当天订单列表:" + JSON.toJSONString(headList));
		
		return JSON.toJSONString(headList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/sale/listOrderByCustomerId/{customerId}", method = RequestMethod.GET)
	public String listOrderByCustomer(@PathVariable Long customerId) {
		return JSON.toJSONString(orderService.findALLByCustomerId(customerId));
	}
	
	@ResponseBody
	@RequestMapping(value = "/sale/listOrderByCustomerByDate/{customerId}/{date}", method = RequestMethod.GET)
	public String listOrderByCustomerByDate(@PathVariable Long customerId, @PathVariable String date) {
		return JSON.toJSONString(orderService.findALLByCustomerByDate(customerId, date));
	}
	
	@ResponseBody
	@RequestMapping(value = "/sale/deleteOrderById/{orderId}", method = RequestMethod.POST)
	public String deleteOrderById(@PathVariable Long orderId) {
		orderService.delete(orderId);
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/sale/deleteItemOfOrderById/{itemId}", method = RequestMethod.POST)
	public String deleteItemOfOrderById(@PathVariable Long itemId) {
		orderDetailService.delete(itemId);
		return "";
	}

}
