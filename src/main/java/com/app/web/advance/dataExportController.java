package com.app.web.advance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/advance/")
public class dataExportController {
	
	@RequestMapping("dataExport")
	public String addUser(){
		return "repository/advance/dataAnalysis";
	}

}
