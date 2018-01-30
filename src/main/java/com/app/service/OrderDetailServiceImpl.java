package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.repository.OrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements 	OrderDetailService {
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Override
	public void delete(Long id) {
		orderDetailRepository.delete(id);	
	}

}
