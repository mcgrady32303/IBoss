package com.app.service;

import java.util.List;

import com.app.entity.OrderHeadEntity;

public interface OrderService {
	List<OrderHeadEntity> findAll();

	void update(OrderHeadEntity order);

	void delete(Long id);

	void save(OrderHeadEntity order);
	
	OrderHeadEntity getOne(Long id);
	
	List<OrderHeadEntity> findALLByDate(String date);
	
	List<OrderHeadEntity> findALLByCustomerId(Long customerId);
	
	List<OrderHeadEntity> findALLByCustomerByDate(Long customerId, String date);
}
