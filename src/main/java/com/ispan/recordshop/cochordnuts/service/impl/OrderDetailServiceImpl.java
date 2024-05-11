package com.ispan.recordshop.cochordnuts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.OrderDetailRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	public List<OrderDetail> selectAll() {

		return orderDetailRepository.findAll();
	}

	public OrderDetail insert(OrderDetail orderDetail) {
		if (orderDetail != null) {
			return orderDetailRepository.save(orderDetail);
		}

		return null;
	}

	public OrderDetail update(OrderDetail orderDetail) {
		if (orderRepository.findById(orderDetail.getOrderNo().getOrderNo()) != null) {
			return orderDetailRepository.save(orderDetail);
		}
		return null;
	}

	public boolean deleteById(Integer ordersNo) {
		if (orderRepository.findById(ordersNo) != null) {
			orderRepository.deleteById(ordersNo);
			return true;
		}
		return false;
	}

	
}
