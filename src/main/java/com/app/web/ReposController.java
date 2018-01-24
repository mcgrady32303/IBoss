package com.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.ItemEntity;
import com.app.pojo.PartDetailsDTO;
import com.app.service.ItemService;
import com.app.utils.ImageSaveUtils;

@Controller
public class ReposController {
	@Autowired
	private ItemService itemService;

	@ResponseBody
	@RequestMapping("/repos/list")
	public String findAll() {

		System.out.println("item列表：" + JSON.toJSONString(itemService.findAll()));

		return JSON.toJSONString(itemService.findAll());
	}
	
	@ResponseBody
	@RequestMapping("/repos/list4Sale")
	public String findAll2() {

		System.out.println("item列表：" + JSON.toJSONString(itemService.findAll()));
		
		StringBuffer result = new StringBuffer("{ items : ");
		result.append(JSON.toJSONString(itemService.findAll()));
		result.append("}");

		return result.toString();
	}
	
	@RequestMapping(value = "/deleteItem")
	@ResponseBody
	public String deleteItem(HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("to delete id : " + request.getParameter("itemId"));

		long itemId = Long.valueOf(request.getParameter("itemId"));
		ItemEntity item = new ItemEntity();
		item.setId(itemId);
		itemService.delete(item);
		
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleFileUpload(
			@ModelAttribute("goodInfo") PartDetailsDTO info,
			BindingResult bindingResult) {

		System.out.println("name: " + info.getGoodName() + " num: "
				+ info.getInitNum() + " image:"
				+ info.getSampleImage().toString());

		if (bindingResult.hasErrors()) {
			System.out.println("There are errors:" + bindingResult.toString());
			return "上传出错！";
		}

		String imageIndex = "-1";

		if (info.getSampleImage().isEmpty()) {
			System.out.println("文件为空！");
			return "文件为空！";
		} else {
			imageIndex = ImageSaveUtils.saveImage(info.getSampleImage(), "item");
		}

		ItemEntity item = new ItemEntity();
		item.setName(info.getGoodName());
		item.setPictureIndex(imageIndex);
		item.setNum(info.getInitNum());
		if (info.getActionType().equals("edit")) {
			item.setId(info.getItemId());
		}
		itemService.save(item);

		return "保存成功！";
	}	
}
