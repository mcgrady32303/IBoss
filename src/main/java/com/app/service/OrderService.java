package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.entity.OrderHeadEntity;

public interface OrderService {
	List<OrderHeadEntity> findAll();
	
	Page<OrderHeadEntity> findAll(Integer page,Integer size);
	
	Page<OrderHeadEntity> findAllByYear(Integer page,Integer size, String year);
	
	Page<OrderHeadEntity> findAllHasDebt(Integer page,Integer size);
	
	Page<OrderHeadEntity> findAllHasDebtByYear(Integer page,Integer size, String year);
	
	void update(OrderHeadEntity order);

	void delete(Long id);

	void save(OrderHeadEntity order);
	
	OrderHeadEntity getOne(Long id);
	
	List<OrderHeadEntity> findALLByDate(String date);
	
	List<OrderHeadEntity> findALLBetweenDate(String start, String end);
	
	List<OrderHeadEntity> findALLByCustomerId(Long customerId);
	
	List<OrderHeadEntity> findALLByCustomerByDate(Long customerId, String date);
}
