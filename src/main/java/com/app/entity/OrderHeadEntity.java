package com.app.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="orderhead")
public class OrderHeadEntity extends BaseEntity {
	
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="customer_id")
	private CustomerEntity customer;
	
	private double totalPay;
	
	private boolean isPayed;
	
	@OneToMany(mappedBy="orderhead",cascade=CascadeType.ALL)
	private List<OrderDetailEntity> orderList = new LinkedList<OrderDetailEntity>();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
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
	
}
