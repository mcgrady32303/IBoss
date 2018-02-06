package com.app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class ImageSaveUtils {

	private static class SingletonHolder{
        private static final ImageSaveUtils instance = new ImageSaveUtils();
    }// final 其实可有可无
    private ImageSaveUtils(){}
    public static ImageSaveUtils getInstance(){
        return SingletonHolder.instance;
    }

   

	public String saveImage(MultipartFile image, String type, String path) {
		return save(image, type, path);
	}

	private String save(MultipartFile image, String type, String path) {

		String suffix = image.getOriginalFilename().substring(
				image.getOriginalFilename().lastIndexOf("."));
		long curTime = System.currentTimeMillis();
		String imageName = "" + curTime + suffix;

		try {
			// Get the file and save it somewhere
			byte[] bytes = image.getBytes();
			Path p = Paths.get(path + imageName);
			Files.write(p, bytes);

			return imageName;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "#1";
	}

}
