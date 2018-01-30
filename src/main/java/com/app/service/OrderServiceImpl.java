package com.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

}
