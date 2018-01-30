package com.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
	
	private String date;
	
	private long customer_id;
	
	private String customerName;
	
	private double totalPrice;
	
	private boolean isPayed;
	
	private List<ItemDTO> orderList = new ArrayList<ItemDTO>();

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isPayed() {
		return isPayed;
	}

	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}

	public List<ItemDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<ItemDTO> orderList) {
		this.orderList = orderList;
	}
	
	public String toString() {
		StringBuffer sb = new  StringBuffer("customer_id: " + customer_id + " Date: " + date);
		for(ItemDTO item : orderList) {
			sb.append(item.toString());
		}
		return sb.toString();
	}
	
	

}
