package com.app.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageSaveUtils {
	
	public static String saveImage(MultipartFile image, String type) {
		return save(image, type);
	}
	
	private static String save(MultipartFile image, String type) {

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
