package com.ispan.recordshop.cochordnuts.service.impl;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrderRepository orderRepository;

	public List<Orders> selectAll() {
		return orderRepository.findAll();
	}

	public Orders insert(Orders orders) {
		if (orders != null) {
			return orderRepository.save(orders);
		}
		return null;

	}

	public void deleteById(Integer ordersNo) {
		orderRepository.deleteById(ordersNo);
	}
	public Orders update(String Json) {
		return null;
	}
}
