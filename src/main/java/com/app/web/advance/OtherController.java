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
	
	@Value("${backup.path}")
	private String path;
	
	@ResponseBody
	@RequestMapping(value = "backup")
	public String backupData() {
		
		String realCMD = cmd + "  > " + path + "backup" + System.currentTimeMillis() + ".sql";
		
		logger.info("start to backup database.command: " + "cmd /k start  \"\"  \"" + realCMD + "\"");
		
		// mysqldump -hhostname -uusername -ppassword databasename > backupfile.sql
		
		try {
			Runtime.getRuntime().exec("cmd /k start  \"\"  \"" + realCMD + "\"");
		} catch (IOException e) {
			logger.info("backup 异常" + e.getMessage());
			return "exception";
		}
		
		return "ok";
		
	}
	
//	public static void main(String[] args) throws IOException {
//		
//		//经过测试，ok
//		String cmd = "cmd /k start  \"\"  \"" + "D:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump.exe -hlocalhost -uroot -proot1234 test > E:\\backupfile.sql" + "\"";
//		
//		System.out.println(cmd);
//		
//		Runtime.getRuntime().exec(cmd);
//		
//	}


}
