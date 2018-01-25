package com.app.pojo;

public class ItemDTO {
	
	private long item_id;
	
	private int num;
	
	private double price;

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toString() {
		return " item_id: " + item_id +" num: " +num + " price: " + price;
	}

}
