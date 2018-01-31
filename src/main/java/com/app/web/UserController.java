package com.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.UserEntity;
import com.app.service.UserService;

@Controller
@RequestMapping("/repository/")
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * find all users
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list")
	public String findAll(){
		return JSON.toJSONString(userService.findAll());
	}
	
	/**
	 * save user object
	 * @return
	 */
	@RequestMapping("post")
	public String post(UserEntity user){
		userService.save(user);
		return "redirect:/repository/home/adduser";
	}
}
