package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 订单中的一项 一个orderHead对应多个orderDetail
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "orderdetail")
public class OrderDetailEntity extends BaseEntity {

	private long itemId;

	private int num;

	private double price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderhead_id")
	private OrderHeadEntity orderHead;

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}


	public OrderHeadEntity getOrderHead() {
		return orderHead;
	}

	public void setOrderHead(OrderHeadEntity orderHead) {
		this.orderHead = orderHead;
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
	
	public int hashCode() {
		return this.hashCode();
	}
	
	public boolean equals(Object obj) {
		return this == obj;
	}

}
