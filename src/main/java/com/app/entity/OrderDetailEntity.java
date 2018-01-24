package com.app.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * 订单中的一项
 * 一个orderHead对应多个orderDetail，一个orderDetail对应一个item
 * @author Administrator
 *
 */
@Entity
@Table(name="orderdetail")
public class OrderDetailEntity extends BaseEntity {
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="item_id")
	private ItemEntity item;
	
	private int num;
	
	private double price;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="orderhead_id")
	private OrderHeadEntity orderhead;

	public OrderHeadEntity getOrder() {
		return orderhead;
	}

	public void setOrder(OrderHeadEntity order) {
		this.orderhead = order;
	}

	public ItemEntity getItem() {
		return item;
	}

	public void setItem(ItemEntity item) {
		this.item = item;
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
	

}
