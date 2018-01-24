package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.OrderHeadEntity;

public interface OrderRepository extends JpaRepository<OrderHeadEntity, Long>{

}
