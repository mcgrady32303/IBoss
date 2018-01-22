package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.CustomerEntity;
import com.app.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<CustomerEntity> findAll() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}

	@Override
	public void update(CustomerEntity item) {
		customerRepository.save(item);
		
	}

	@Override
	public void delete(CustomerEntity item) {
		customerRepository.delete(item);		
	}

	@Override
	public void save(CustomerEntity item) {
		customerRepository.save(item);
		
	}

}
