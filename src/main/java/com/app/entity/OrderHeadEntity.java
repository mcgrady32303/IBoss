package com.app.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


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
	
	@Transient
	private String customerName;
	
	@Transient
	private String actionType;

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	private double totalPay;

	private boolean payed;

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean isPayed) {
		this.payed = isPayed;
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
