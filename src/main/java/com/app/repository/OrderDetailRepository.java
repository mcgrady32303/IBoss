package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.OrderDetailEntity;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

}
