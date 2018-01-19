package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.AddressEntity;
import com.app.entity.UserEntity;
import com.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 查询所有的User对象
	 * @return
	 */
	public List<UserEntity> findAll(){
		return userRepository.findAll();
	}
	
	/**
	 * save user
	 * @param user
	 */
	public void save(UserEntity user) {
		for(AddressEntity address: user.getAddresses()) {
			address.setUser(user);
		}
		userRepository.save(user);
	}
}
