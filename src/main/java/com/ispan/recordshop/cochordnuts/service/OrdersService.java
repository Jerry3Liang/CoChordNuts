package com.ispan.recordshop.cochordnuts.service;

import java.util.List;
import java.util.Optional;

import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;


public interface OrdersService{
	
	
	public List<Orders>selectAll();
	public Orders insert(Orders orders);
	public Orders update(Orders orders);
	public boolean deleteById(Integer ordersNo);
	public Optional<Orders> findByOrderNo(Integer ordersNo);
	public List<OrderDetail> findOrderDetail(Integer ordersNo);

	

}
