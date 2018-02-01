package com.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.utils.CurrentUserUtils;

@Controller
@RequestMapping("/repository/home/")
public class HomeController {
	
	/**
	 * 处理图片生成
	 * @return
	 */
	@RequestMapping("sale")
	public String holder(){
		return "repository/customer/sale";
	}
	
	/**
	 * 处理模板方法
	 * @return
	 */
	@RequestMapping("tmpl")
	public String tmpl(){
		return "repository/customer/template";
	}
	
	@RequestMapping("adduser")
	public String addUser(){
		return "repository/customer/customer-manage";
	}
	
	@RequestMapping("logout")
	public String logout() {
		CurrentUserUtils.getInstance().serUser(null);
		return "login";
	}
}
