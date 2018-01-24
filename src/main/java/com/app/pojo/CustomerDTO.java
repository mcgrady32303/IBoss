package com.app.pojo;

import org.springframework.web.multipart.MultipartFile;

public class CustomerDTO {
	private String customerName;
	private String actionType;
	private long customerId;
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private MultipartFile sampleImage;
	public MultipartFile getSampleImage() {
		return sampleImage;
	}
	public void setSampleImage(MultipartFile sampleImage) {
		this.sampleImage = sampleImage;
	}
	private String tel;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}	

}
