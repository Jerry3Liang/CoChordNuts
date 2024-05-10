package com.ispan.recordshop.cochordnuts.service;

import java.util.List;

import com.ispan.recordshop.cochordnuts.model.OrderDetail;


public interface OrderDetailService {

	public List<OrderDetail>selectAll();
	public OrderDetail insert(String Json);
	public OrderDetail update(String Json);
	public void deleteById(Integer ordersNo);
}
