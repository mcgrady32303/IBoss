package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.OrderHeadEntity;
import com.app.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<OrderHeadEntity> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public void update(OrderHeadEntity order) {
		orderRepository.save(order);
	}

	@Override
	public void delete(OrderHeadEntity order) {
		orderRepository.delete(order);

	}

	@Override
	public void save(OrderHeadEntity order) {
		orderRepository.save(order);
	}

}
