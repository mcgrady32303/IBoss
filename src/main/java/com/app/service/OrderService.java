package com.app.service;

import java.util.List;

import com.app.entity.OrderHeadEntity;

public interface OrderService {
	List<OrderHeadEntity> findAll();

	void update(OrderHeadEntity order);

	void delete(OrderHeadEntity order);

	void save(OrderHeadEntity order);
	
	OrderHeadEntity getOne(long id);

}
