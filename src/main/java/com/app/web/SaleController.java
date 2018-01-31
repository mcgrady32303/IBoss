package com.app.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
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

		logger.debug("to save order entity:" + JSON.toJSONString(order));

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
		assembleOrder(order);
		return JSON.toJSONString(order);
	}

	@ResponseBody
	@RequestMapping(value = "/sale/listOrderByDate/{date}", method = RequestMethod.GET)
	public String listOrderByDate(@PathVariable String date) {

		List<OrderHeadEntity> headList = orderService.findALLByDate(date);

		if (!headList.isEmpty()) {
			for (OrderHeadEntity oneOrder : headList) { // 将customer名字查询出来,并赋值到订单body中
				assembleOrder(oneOrder);
			}
		}

		logger.info(date + "当天订单列表:" + JSON.toJSONString(headList));
		return JSON.toJSONString(headList);
	}

	@ResponseBody
	@RequestMapping(value = "/sale/listOrderByCustomerId/{customerId}", method = RequestMethod.GET)
	public String listOrderByCustomer(@PathVariable Long customerId) {
		logger.debug("根据客户id查询:" + customerId);

		List<OrderHeadEntity> headList = orderService
				.findALLByCustomerId(customerId);

		if (!headList.isEmpty()) {
			for (OrderHeadEntity oneOrder : headList) { // 将customer名字查询出来,并赋值到订单body中
				assembleOrder(oneOrder);
			}
		}

		return JSON.toJSONString(headList);
	}

	@ResponseBody
	@RequestMapping(value = "/sale/listOrderByCustomerByDate/{customerId}/{date}", method = RequestMethod.GET)
	public String listOrderByCustomerByDate(@PathVariable Long customerId,
			@PathVariable String date) {
		logger.debug("根据客户id与日期组合查询:" + customerId + ", " + date);
		
		List<OrderHeadEntity> headList = orderService.findALLByCustomerByDate(
				customerId, date);

		if (!headList.isEmpty()) {
			for (OrderHeadEntity oneOrder : headList) { // 将customer与item名字查询出来,并赋值到订单body中
				assembleOrder(oneOrder);
			}
		}

		return JSON.toJSONString(headList);
	}

	@ResponseBody
	@RequestMapping(value = "/sale/deleteOrderById/{orderId}", method = RequestMethod.POST)
	public String deleteOrderById(@PathVariable Long orderId) {
		logger.debug("删除订单:" + orderId);
		orderService.delete(orderId);
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/sale/deleteItemOfOrderById/{itemId}", method = RequestMethod.POST)
	public String deleteItemOfOrderById(@PathVariable Long itemId) {
		logger.debug("删除订单中的item" + itemId);
		orderDetailService.delete(itemId);
		return "";
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
			if(ie != null) {
				ode.setItemName(ie.getName());
			} else {
				ode.setItemName("找不到材料名");
			}
			
		}
	}
}
