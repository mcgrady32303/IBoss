package com.app.web.advance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
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
import com.app.service.OrderService;

@Controller
@RequestMapping("/advance/")
public class dataExportController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<ItemEntity> items;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ItemService itemService;

	@RequestMapping("dataExport")
	public String addUser() {
		return "repository/advance/dataExport";
	}

	@RequestMapping("report")
	public String report() {
		return "repository/advance/report";
	}

	@RequestMapping("other")
	public String other() {
		return "repository/advance/other";
	}

	@ResponseBody
	@RequestMapping(value = "export/all", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<String> headers = generateHeader();

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 18);
		
		//表头
		HSSFRow head = sheet.createRow(0);
		for (short i = 0; i < headers.size(); i++) {
			HSSFCell cellOfHead = head.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
			cellOfHead.setCellValue(text);
		}

		boolean isLast = false;
		int startPage = 0;
		int index = 0; //数据内容行号
		while (!isLast) {
			List<OrderHeadEntity> headList = new ArrayList<OrderHeadEntity>();
			isLast = listAllOrderPageable(headList, startPage, 3);
			
			logger.info("第"+startPage + "页订单列表：" + headList);
			
			logger.info("isLast: " + isLast);

			//每一行数据
			for(OrderHeadEntity ohe : headList) {
				HSSFRow row = sheet.createRow(index+1);
				index++;
				
				HSSFCell id = row.createCell(0);
				id.setCellValue(ohe.getId());
				
				HSSFCell name = row.createCell(1);
				name.setCellValue(ohe.getCustomerName());
				
				HSSFCell total = row.createCell(2);
				total.setCellValue(ohe.getTotalPay());
				
				HSSFCell isPay = row.createCell(3);
				isPay.setCellValue(ohe.isPayed()?"是":"否");
				
				//headers包含前4个固定标签，后面每个材料循环生成
				for(int i = 0; i < headers.size()-4; i++) {
					HSSFCell cellOfRow = row.createCell(i+4);
					long itemTolSum = 0;
					long itemId = items.get(i).getId();
					for(OrderDetailEntity ode : ohe.getOrderList()) {
						if(itemId == ode.getItemId()) {
							itemTolSum += ode.getNum();
						}
					}
					cellOfRow.setCellValue(itemTolSum);
				}
			}
			
			startPage++;
		}
		
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition",
				"attachment;filename=" + new String("全部订单".getBytes(),"iso-8859-1") + ".xls");// 默认Excel名称
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	private List<String> generateHeader() {
		this.items = itemService.findAll();
		List<String> headers = new ArrayList<String>();
		headers.add("id");
		headers.add("姓名");
		headers.add("金额");
		headers.add("已支付");
		for (ItemEntity item : items) {
			headers.add(item.getName());
//			headers.add("数量");
		}
		
		logger.info("header: " + JSON.toJSONString(headers));

		return headers;
	}

	//如果最后一页了,则返回真
	private boolean listAllOrderPageable(List<OrderHeadEntity> headList,
			Integer page, Integer size) {

		Page<OrderHeadEntity> orderList = orderService.findAll(page, size);
		for (OrderHeadEntity ohe : orderList.getContent()) {
			assembleOrder(ohe);
			headList.add(ohe);
		}
		
		logger.debug("size :" + orderList.getSize());
		logger.debug("number :" + orderList.getNumberOfElements());
		
		return size > orderList.getNumberOfElements();

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
			if (ie != null) {
				ode.setItemName(ie.getName());
			} else {
				ode.setItemName("找不到材料名");
			}

		}
	}


}
