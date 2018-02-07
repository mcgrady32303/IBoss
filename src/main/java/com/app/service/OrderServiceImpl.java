package com.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.entity.OrderHeadEntity;
import com.app.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<OrderHeadEntity> findAll() {
		return orderRepository.findAll();
	}
	
	@Override
	public Page<OrderHeadEntity> findAll(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");  
        return orderRepository.findAll(pageable);  
	}


	@Override
	public void update(OrderHeadEntity order) {
		orderRepository.save(order);
	}

	@Override
	public void delete(Long id) {
		orderRepository.delete(id);

	}

	@Override
	public void save(OrderHeadEntity order) {
		orderRepository.save(order);
	}

	@Override
	public OrderHeadEntity getOne(Long id) {
		return orderRepository.findOne(id);
	}

	@Override
	public List<OrderHeadEntity> findALLByDate(String date) {

		final String paramDate = date; // 传参到内部类必须为final类型

		if (StringUtils.isBlank(paramDate)) {
			System.out.println("日期为空!");
		}

		return orderRepository.findAll(new Specification<OrderHeadEntity>() {
			@Override
			public Predicate toPredicate(Root<OrderHeadEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.equal(root.get("date").as(String.class), paramDate));

				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}

		});

	}

	@Override
	public List<OrderHeadEntity> findALLByCustomerId(Long customerId) {
		final Long paramCustomerId = customerId; // 传参到内部类必须为final类型

		return orderRepository.findAll(new Specification<OrderHeadEntity>() {
			@Override
			public Predicate toPredicate(Root<OrderHeadEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.equal(root.get("customerId").as(Long.class),
						paramCustomerId));

				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}

		});
	}

	@Override
	public List<OrderHeadEntity> findALLByCustomerByDate(Long customerId,
			String date) {
		final String paramDate = date; // 传参到内部类必须为final类型
		final Long paramCustomerId = customerId; // 传参到内部类必须为final类型
		
		return orderRepository.findAll(new Specification<OrderHeadEntity>() {
			@Override
			public Predicate toPredicate(Root<OrderHeadEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.equal(root.get("customerId").as(Long.class),
						paramCustomerId));
				list.add(cb.equal(root.get("date").as(String.class), paramDate));

				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}

		});
	}

	@Override
	public List<OrderHeadEntity> findALLBetweenDate(String start, String end) {
		final String startDate = start; // 传参到内部类必须为final类型
		final String endDate = end; // 传参到内部类必须为final类型

		if (StringUtils.isBlank(startDate)) {
			System.out.println("开始日期为空!");
		}
		
		if (StringUtils.isBlank(endDate)) {
			System.out.println("结束日期为空!");
		}

		return orderRepository.findAll(new Specification<OrderHeadEntity>() {
			@Override
			public Predicate toPredicate(Root<OrderHeadEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.between(root.get("date").as(String.class), startDate, endDate));

				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}

		});
	}

	
}
