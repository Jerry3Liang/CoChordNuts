package com.ispan.recordshop.cochordnuts.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.OrderDetailRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;


@Service
public class OrdersServiceImpl  {

	@Autowired
	private OrderRepository orderRepository;
	
	public List<Orders> selectAll() {//搜尋全部
		return orderRepository.findAll();
	}
	public Orders insert(Orders orders) {//新增
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
	
	public Orders update(Integer orderNo,Orders orders) {//修改訂單
		Orders oldOrders=orderRepository.findById(orderNo).get();
		if(oldOrders!=null && orderRepository.findById(orderNo)!=null) {
			    orders.setOrderNo(orderNo);
				return orderRepository.save(orders);											
		}	
		return null;
	}
	
	public Optional<Orders> findByOrderNo(Integer ordersNo) {//單筆搜尋
		
		return orderRepository.findById(ordersNo);
	}
	public List<OrderDetail> findOrderDetail(Integer ordersNo){//訂單詳細內容
		return orderRepository.findById(ordersNo).get().getOrderDetail();		
	}
	
	public List<Orders> findBymemberNo(Integer MemberNo) {// 依會員編號查詢訂單

			return orderRepository.findBymemberNo(MemberNo);

	}
}
