package com.app.web.advance;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/other/")
public class OtherController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${backup.command}")
	private String cmd;

	
	@ResponseBody
	@RequestMapping(value = "backup")
	public String backupData() {
		
		logger.info("start to backup database.command: cmd /k start " + cmd);
		
		// mysqldump -hhostname -uusername -ppassword databasename > backupfile.sql
		
		try {
			Runtime.getRuntime().exec("cmd /k start " + cmd);
		} catch (IOException e) {
			logger.info("backup 异常" + e.getMessage());
			return "exception";
		}
		
		return "ok";
		
	}


}
