package com.app.service;

import java.util.List;

import com.app.entity.OrderDetailEntity;


public interface OrderDetailService {
	
	void delete(Long id);
	
	List<OrderDetailEntity> findByItemId(Long itemId);
	
}
