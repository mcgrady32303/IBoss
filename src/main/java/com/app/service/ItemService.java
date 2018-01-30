package com.app.service;

import java.util.List;

import com.app.entity.ItemEntity;

public interface ItemService {
	
	/**
	 * 查询所有库存
	 * @return
	 */
	List<ItemEntity> findAll();
	
	/**
	 * 修改库存属性
	 * @param item
	 */
	void update(ItemEntity item);
	
	void delete(ItemEntity item);
	
	void save(ItemEntity item);
	
	ItemEntity findOne(Long id);

}
