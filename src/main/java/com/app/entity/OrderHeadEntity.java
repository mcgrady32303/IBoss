package com.app.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
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
@Table(name = "orderhead",
       indexes={@Index(name="date_index", columnList="date")})
public class OrderHeadEntity extends BaseEntity {

	private String date;

	private long customerId;
	
	@Transient
	private String customerName;
	
	@Transient
	private String actionType;
	
	private String receiptNo;

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	private double totalPay;
	
	//欠款（未支付时，debt=totalPay）
	private double debt;

	//{0:已支付，1:未支付，2:部分支付}
	private int paymentStatus;

	@OneToMany(mappedBy = "orderHead", cascade = CascadeType.ALL)
	private List<OrderDetailEntity> orderList = new LinkedList<OrderDetailEntity>();

	
	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

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
