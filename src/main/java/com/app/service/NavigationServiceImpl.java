package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.BaseEntity;
import com.app.entity.NavigationEntity;
import com.app.repository.NavigationRepository;

@Service
public class NavigationServiceImpl implements NavigationService {
	@Autowired
	private NavigationRepository navigationRepository;
	
	/**
	 * 根据type查询菜单
	 * @param type
	 * @return
	 */
	public List<NavigationEntity> findByType(int type) {
		return navigationRepository.findByTypeAndStatusIs(type, BaseEntity.STATUS_VALID);
	}
}
