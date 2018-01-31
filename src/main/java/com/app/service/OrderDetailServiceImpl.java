package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.OrderDetailEntity;
import com.app.repository.OrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Override
	public void delete(Long id) {
		orderDetailRepository.delete(id);
	}

	@Override
	public List<OrderDetailEntity> findByItemId(Long itemId) {
		return orderDetailRepository.findByItemId(itemId);
	}

}
