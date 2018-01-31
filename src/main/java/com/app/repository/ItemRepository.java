package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.ItemEntity;

public interface ItemRepository  extends JpaRepository<ItemEntity, Long>{
	

}
