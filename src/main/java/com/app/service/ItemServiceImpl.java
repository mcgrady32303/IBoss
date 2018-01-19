package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.ItemEntity;
import com.app.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public List<ItemEntity> findAll() {
		return itemRepository.findAll();
	}

	@Override
	public void update(ItemEntity item) {
		itemRepository.save(item);
		
	}

	@Override
	public void delete(ItemEntity item) {
		itemRepository.delete(item);
		
	}

	@Override
	public void save(ItemEntity item) {
		itemRepository.save(item);		
	}

}
