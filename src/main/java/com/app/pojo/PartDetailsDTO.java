package com.app.pojo;

import org.springframework.web.multipart.MultipartFile;

public class PartDetailsDTO {

	private String goodName;
	private String actionType;
	private long itemId;
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	private MultipartFile sampleImage;
	private long initNum;
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public MultipartFile getSampleImage() {
		return sampleImage;
	}
	public void setSampleImage(MultipartFile sampleImage) {
		this.sampleImage = sampleImage;
	}
	public long getInitNum() {
		return initNum;
	}
	public void setInitNum(long initNum) {
		this.initNum = initNum;
	}
}
