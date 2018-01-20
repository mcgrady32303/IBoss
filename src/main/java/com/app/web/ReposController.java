package com.app.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.app.entity.ItemEntity;
import com.app.pojo.PartDetailsDTO;
import com.app.service.ItemService;

@Controller
public class ReposController {
	@Autowired
	private ItemService itemService;

	@ResponseBody
	@RequestMapping("/repos/list")
	public String findAll() {

		System.out.println(JSON.toJSONString(itemService.findAll()));

		return JSON.toJSONString(itemService.findAll());
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

		String imageIndex="-1";

		if (info.getSampleImage().isEmpty()) {
			System.out.println("文件为空！");
			return "文件为空！";
		} else {
			imageIndex = saveImage(info.getSampleImage());
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

	private String saveImage(MultipartFile image) {

		String suffix = image.getOriginalFilename().substring(
				image.getOriginalFilename().lastIndexOf("."));
		long curTime = System.currentTimeMillis();
		String imageName = "" + curTime + suffix;

		try {
			String UPLOAD_PREFIX = "/";
			File p = new File(ResourceUtils.getURL("classpath:").getPath());
			if (p.exists()) {
				UPLOAD_PREFIX = p.getAbsolutePath() + "/static/images/";
			}

			// Get the file and save it somewhere
			byte[] bytes = image.getBytes();
			Path path = Paths.get(UPLOAD_PREFIX + imageName);
			Files.write(path, bytes);

			return imageName;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "-1";
	}

}
