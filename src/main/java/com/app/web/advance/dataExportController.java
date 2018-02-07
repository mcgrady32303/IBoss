package com.app.web.advance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.pojo.CustomerSimpleDTO;

@Controller
@RequestMapping("/advance/")
public class dataExportController {

	@RequestMapping("dataExport")
	public String addUser() {
		return "repository/advance/dataAnalysis";
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
	public void exportAll(HttpServletRequest request,
			HttpServletResponse response) throws IOException {// ,@RequestParam
																// String
																// startTime,
		// @RequestParam String endTime) {
		List<CustomerSimpleDTO> dataset = new ArrayList<CustomerSimpleDTO>();
		String[] headers = { "姓名", "总金额", "未付金额" };// 导出的Excel头部，这个要根据自己项目改一下

		CustomerSimpleDTO csd1 = new CustomerSimpleDTO();
		CustomerSimpleDTO csd2 = new CustomerSimpleDTO();

		csd1.setName("111");
		csd2.setName("222");
		dataset.add(csd1);
		dataset.add(csd2);

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 18);
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		int index = 0;
		for (CustomerSimpleDTO csd : dataset) {
			index++;
			HSSFRow row1 = sheet.createRow(index);

			HSSFCell cell0 = row1.createCell(0);
			HSSFRichTextString richString0 = new HSSFRichTextString(
					csd.getName());
			HSSFFont font0 = workbook.createFont();
			font0.setColor(HSSFColor.BLUE.index);// 定义Excel数据颜色
			richString0.applyFont(font0);
			cell0.setCellValue(richString0);

			HSSFCell cell1 = row1.createCell(1);
			cell1.setCellValue(csd.getTotalToPay());

			HSSFCell cell2 = row1.createCell(2);
			cell2.setCellValue(csd.getTotalUnpay());
		}
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition",
				"attachment;filename=test.xls");// 默认Excel名称
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

}
