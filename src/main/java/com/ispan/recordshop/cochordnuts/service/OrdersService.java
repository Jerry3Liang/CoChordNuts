package com.ispan.recordshop.cochordnuts.service;

import java.util.List;

import com.ispan.recordshop.cochordnuts.model.Orders;


public interface OrdersService{
	
	
	public List<Orders>selectAll();
	public Orders insert(Orders orders);
	public Orders update(String Json);
	public void deleteById(Integer ordersNo);

	

}
