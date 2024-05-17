package com.ispan.recordshop.cochordnuts.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.CartForOrdersDto;
import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;


@Service
public class OrdersServiceImpl  {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MemberRepository memberRepo;
	
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
//			Member m = memberRepository.findById(1).get();
			
			oldOrders.setOrderNo(orderNo);
//			oldOrders.setRecipientAddress(orders.getRecipientAddress());
//			oldOrders.setRecipient(orders.getRecipient());
//			oldOrders.setRecipientPhone(orders.getRecipientPhone());
//			oldOrders.setDeliverType(orders.getDeliverType());
			oldOrders.setStatus();
			    
				return orderRepository.save(oldOrders);											
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
	public List<CartForOrdersDto> findCartByMember(Integer memberNo) {
		List<CartForOrdersDto> cartArray = new ArrayList<>();
		List<Map<String, Object>> results = orderRepository.findCartByMemberNo(memberNo);
		if(results!=null) {			
			for (Map<String, Object> row : results) {
				CartForOrdersDto cart = new CartForOrdersDto();//將MAP取得的資料丟進新建的CartDto
				cart.setCount((Integer)row.get("Count"));			
				Integer productNo =(Integer) row.get("product_productNo");//取得Map中會員編號
				Product product=productRepository.findById(productNo).get();//依會員編號取得product物件		
				//將取得的product物件丟入Cart
				cart.setDiscount(product.getDiscount());//折扣
				cart.setUnitPrice(product.getUnitPrice());//單價
				cart.setProductName(product.getProductName());//商品名稱
				cart.setProductNo(productNo);//商品編號
				cart.setTotal();
				cartArray.add(cart);//cart存入陣列
			}
		
		
	}
		return cartArray;
}
	
	
	public List<CartForOrdersDto> findCartByMember2(Integer memberNo,Orders order) {
		List<CartForOrdersDto> cartArray = new ArrayList<>();
		List<Map<String, Object>> results = orderRepository.findCartByMemberNo(memberNo);
		if(results!=null) {			
			for (Map<String, Object> row : results) {
				CartForOrdersDto cart = new CartForOrdersDto();//將MAP取得的資料丟進新建的CartDto
				cart.setCount((Integer)row.get("Count"));	
				cart.setOrderNo(order.getOrderNo());
				Integer productNo =(Integer) row.get("product_productNo");//取得Map中會員編號
				Product product=productRepository.findById(productNo).get();//依會員編號取得product物件		
				//將取得的product物件丟入Cart
				cart.setDiscount(product.getDiscount());//折扣
				cart.setUnitPrice(product.getUnitPrice());//單價
				cart.setProductName(product.getProductName());//商品名稱
				cart.setProductNo(productNo);//商品編號
				cart.setTotal();
				cartArray.add(cart);//cart存入陣列
			}
		
		
	}
		return cartArray;
}
	
	
}