package com.ispan.recordshop.cochordnuts.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.OrderDetailRepository;
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
	
	public boolean deleteById(Integer ordersNo) {//刪除訂單
		if(orderRepository.findById(ordersNo)!=null) {
			orderRepository.deleteById(ordersNo);
			return true;
			
		}else {
			return false;
		}		
	}
	
	public Orders update(Orders orders) {//修改訂單
		if(orderRepository.findById(orders.getOrderNo())!=null) {
			return orderRepository.save(orders);			
		}	
		return null;
	}
	
	public Optional<Orders> findByOrderNo(Integer ordersNo) {//找訂單
		
		return orderRepository.findById(ordersNo);
	}
	public List<OrderDetail> findOrderDetail(Integer ordersNo){//找訂單詳細內容
		return orderRepository.findById(ordersNo).get().getOrderDetail();		
	}
	
}
