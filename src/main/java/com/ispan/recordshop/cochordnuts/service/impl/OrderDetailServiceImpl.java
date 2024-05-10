package com.ispan.recordshop.cochordnuts.service.impl;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.OrderDetailRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;
import com.ispan.recordshop.cochordnuts.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	public List<OrderDetail>selectAll(){return null;}
	
	
	
	public OrderDetail insert(String Json) {
		OrderDetail orderDetail = new OrderDetail();
		JSONObject obj = new JSONObject(Json);
		Integer productTotalPay= obj.isNull("productTotalPay")?null: obj.getInt("productTotalPay");
		Double discount= obj.isNull("discount")?null: obj.getDouble("discount");		
		Integer productBoughtCount = obj.isNull("productBoughtCount")?null: obj.getInt("productBoughtCount");
		Integer orderNo= obj.isNull("orderNo")?null: obj.getInt("orderNo");
		Integer productNo= obj.isNull("productNo")?null: obj.getInt("productNo");
		
		
		Optional<Product> product=productRepository.findById(productNo);
		Optional<Orders> order = orderRepository.findById(orderNo);
		
		
		
		if(!order.isEmpty()&& !product.isEmpty() ) {			
			orderDetail.setProductTotalPay(productTotalPay);
			orderDetail.setDiscount(discount);
			orderDetail.setProductBoughtCount(productBoughtCount);
			orderDetail.setOrderNo(order.get());
		    orderDetail.setProductNo(product.get());
		    return orderDetailRepository.save(orderDetail);
		}else {
			return null;
		}
					
		}
	public OrderDetail update(String Json) {return null;}
	public void deleteById(Integer ordersNo) {}
}
