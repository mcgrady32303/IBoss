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

	@Value("${backup.path}")
	private String path;

	@ResponseBody
	@RequestMapping(value = "backup")
	public String backupData() {
		return doBackUp();
	}

	private String doBackUp() {
		String realCMD = "cmd /k start " + path + " "
				+ System.currentTimeMillis();
		logger.info("start to backup database.command: " + realCMD);

		int exitValue = -1;

		try {
			Process p = Runtime.getRuntime().exec(realCMD);
			p.waitFor();

			exitValue = p.exitValue(); // 接收执行完毕的返回值

			p.destroy(); // 销毁子进程
			p = null;
		} catch (IOException e) {
			logger.info("backup 异常" + e.getMessage());
			return "exception";
		} catch (InterruptedException e) {
			logger.info("backup 异常" + e.getMessage());
			return "exception";
		}

		if (exitValue == 0) {
			logger.info("备份执行完成.");
		} else {
			logger.error("备份执行失败.");
			return "failed";
		}

		return "ok";
	}

	/**
	 * spring boot 定时任务
	 */
//	@Scheduled(cron = "0 1-2 * * * ? ")
//	public void scheduledBackup() {
//		logger.info("自动调度备份");
//		doBackUp();
//	}

	// public static void main(String[] args) throws IOException {
	//
	// // 经过测试，ok
	// String cmd = "cmd /c start E:\\workspace\\IBoss\\backup.bat "
	// + System.currentTimeMillis();
	// System.out.println(cmd);
	//
	// Runtime.getRuntime().exec(cmd);
	//
	// }

}
