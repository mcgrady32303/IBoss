package com.app.service;

import java.util.List;

import com.app.entity.CustomerEntity;

public interface CustomerService {
	
	List<CustomerEntity> findAll();

	void update(CustomerEntity item);

	void delete(CustomerEntity item);

	void save(CustomerEntity item);
	
	CustomerEntity findOne(Long id);

}
