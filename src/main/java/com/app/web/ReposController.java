package com.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.ItemEntity;
import com.app.entity.OrderDetailEntity;
import com.app.pojo.PartDetailsDTO;
import com.app.service.ItemService;
import com.app.service.OrderDetailService;
import com.app.utils.ImageSaveUtils;

@Controller
public class ReposController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ItemService itemService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Value("${web.upload-path}")
	private String path;

	@ResponseBody
	@RequestMapping("/repos/list")
	public String findAll() {

		logger.info("item列表：" + JSON.toJSONString(itemService.findAll()));

		return JSON.toJSONString(itemService.findAll());
	}

	@ResponseBody
	@RequestMapping(value = "/repos/increaseItem/{itemId}/{addedNum}", method = RequestMethod.GET)
	public String increaseItem(@PathVariable Long itemId,
			@PathVariable int addedNum) {

		ItemEntity item = itemService.findOne(itemId);
		item.setNum(item.getNum() + addedNum);
		itemService.save(item);

		logger.info(itemId + "入庫：" + addedNum);

		return "success";
	}

	@ResponseBody
	@RequestMapping("/repos/list4Sale")
	public String findAll2() {
		List<ItemEntity> items = itemService.findAll();
		StringBuffer result = new StringBuffer("{ \"items\" : ");
		result.append(JSON.toJSONString(items));
		result.append("}");

		logger.info("item列表：" + result.toString());

		return result.toString();
	}

	@RequestMapping(value = "/deleteItem")
	@ResponseBody
	public String deleteItem(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("to delete id : " + request.getParameter("itemId"));

		long itemId = Long.valueOf(request.getParameter("itemId"));
		ItemEntity item = new ItemEntity();
		item.setId(itemId);
		itemService.delete(item);

		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/deleteItem/{id}", method = RequestMethod.GET)
	public String deleteItem(@PathVariable Long id) {

		logger.info("to delete id : " + id);

		if (hasUsedByOrder(id)) {
			logger.error("被删除材料仍被订单使用,不能删除!");
			return "被删除材料仍被订单使用,不能删除!";
		}

		itemService.delete(id);

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleFileUpload(
			@ModelAttribute("goodInfo") PartDetailsDTO info,
			BindingResult bindingResult) {

		logger.info("name: " + info.getGoodName() + " num: "
				+ info.getInitNum() + " image:"
				+ info.getSampleImage().toString());

		if (bindingResult.hasErrors()) {
			logger.error("There are errors:" + bindingResult.toString());
			return "上传出错！";
		}

		String imageIndex = "#";

		if (info.getSampleImage().isEmpty()) {
			logger.info("文件为空！");
			imageIndex = info.getOriginImageIndex();
			if (info.getActionType().equals("add")) {
				imageIndex = "images/defaultItem.jpg";
			}
		} else {
			imageIndex = ImageSaveUtils.getInstance().saveImage(
					info.getSampleImage(), "item", path);
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

	private boolean hasUsedByOrder(Long id) {
		List<OrderDetailEntity> list = orderDetailService.findByItemId(id);

		return !list.isEmpty();
	}
}
