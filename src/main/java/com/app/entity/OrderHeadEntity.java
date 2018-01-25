package com.app.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * 客户某次购买的订单
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "orderhead")
public class OrderHeadEntity extends BaseEntity {

	private String date;

	private long customerId;

	private double totalPay;

	private boolean isPayed;

	@OneToMany(mappedBy = "orderHead", cascade = CascadeType.ALL)
	private List<OrderDetailEntity> orderList = new LinkedList<OrderDetailEntity>();

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public double getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}

	public boolean isPayed() {
		return isPayed;
	}

	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}

	public List<OrderDetailEntity> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDetailEntity> orderList) {
		this.orderList = orderList;
	}

	public void addOrderDetailEntity(OrderDetailEntity ode) {
		if (!orderList.contains(ode)) {
			ode.setOrderHead(this);
			this.orderList.add(ode);
		}
	}
}
