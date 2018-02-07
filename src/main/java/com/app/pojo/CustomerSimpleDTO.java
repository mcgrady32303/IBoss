package com.app.pojo;

public class CustomerSimpleDTO {
	
	private String name;
	
	private int orderNum;
	
	private double totalToPay;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public double getTotalToPay() {
		return totalToPay;
	}

	public void setTotalToPay(double totalToPay) {
		this.totalToPay = totalToPay;
	}

	public double getTotalUnpay() {
		return totalUnpay;
	}

	public void setTotalUnpay(double totalUnpay) {
		this.totalUnpay = totalUnpay;
	}

	private double totalUnpay;

}
