package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>{

}
