package com.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.CustomerEntity;
import com.app.entity.OrderHeadEntity;
import com.app.pojo.CustomerDTO;
import com.app.service.CustomerService;
import com.app.service.OrderService;
import com.app.utils.ImageSaveUtils;

@Controller
public class CustomerController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;

	@ResponseBody
	@RequestMapping("/getAllCustomers")
	public String findAll() {
		return JSON.toJSONString(customerService.findAll());
	}

	@ResponseBody
	@RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
	public String singleFileUpload(
			@ModelAttribute("customerInfo") CustomerDTO info,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			System.out.println("There are errors:" + bindingResult.toString());
			return "上传出错！";
		}

		String imageIndex = "#";

		if (info.getSampleImage().isEmpty()) {
			logger.info("文件为空！");
			imageIndex = info.getOriginImageIndex();
			if(info.getActionType().equals("add")) {
				imageIndex = "defaultCustomer.jpg";
			}
		} else {
			imageIndex = ImageSaveUtils.saveImage(info.getSampleImage(), "customer");
		}

		System.out.println("msg： " + info.getMsg());
		CustomerEntity customer = new CustomerEntity();
		customer.setName(info.getCustomerName());
		customer.setTel(info.getTel());
		customer.setPictureIndex(imageIndex);
		customer.setMsg(info.getMsg());
		if (info.getActionType().equals("edit")) {
			customer.setId(info.getCustomerId());
		}
		customerService.save(customer);

		return "保存成功！";
	}
	
	@RequestMapping(value = "/deleteCustomer")
	@ResponseBody
	public String deleteItem(HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("to delete id : " + request.getParameter("customerId"));

		long customerId = Long.valueOf(request.getParameter("customerId"));
		CustomerEntity customer = new CustomerEntity();
		customer.setId(customerId);
		customerService.delete(customer);
		
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteCustomer/{id}", method = RequestMethod.GET)
	public String deleteItem(@PathVariable Long id) {

		logger.info("to delete id : " + id);
		
		if(hasUsedByOrder(id)) {
			logger.error("被删除客户仍被订单使用,不能删除!");
			return "被删除客户仍被订单使用,不能删除!";
		}

		customerService.delete(id);

		return "success";
	}
	
	private boolean hasUsedByOrder(Long id) {
		List<OrderHeadEntity> list = orderService.findALLByCustomerId(id);
		
		return !list.isEmpty();
	}

}
