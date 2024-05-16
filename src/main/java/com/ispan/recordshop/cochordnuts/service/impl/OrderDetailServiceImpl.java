package com.ispan.recordshop.cochordnuts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderDetailRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;
import com.ispan.recordshop.cochordnuts.service.ProductService;

@Service
public class OrderDetailServiceImpl {
//	@PersistenceContext
//	private EntityManager entityManager;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberRepository memberRepo;

	public List<OrderDetail> selectAll() {

		return orderDetailRepository.findAll();
	}

	@Transactional
	public boolean insert(Orders od) {// 新增OrderDetail

		List<Cart> cartArray = new ArrayList<>();

		List<Map<String, Object>> results = orderRepository.findCartByMemberNo(1);
		results.forEach((list) -> System.out.println(list.entrySet()));
		if(results!=null) {
		
			for (Map<String, Object> row : results) {
				Cart cart = new Cart();
				cart.setProduct(productRepository.findById(1).get());
				cart.setCount((Integer) row.get("Count"));
				cart.setMember(memberRepo.findById(1).get());
				cartArray.add(cart);
								
			}
			OrderDetail orderdetail = new OrderDetail();
			for (Cart aCart : cartArray) {
				orderdetail.setProductBoughtCount(aCart.getCount());
				orderdetail.setProductNo(aCart.getProduct());
				orderdetail.setDiscount();
				orderdetail.setOrderNo(od);			
				orderdetail.setProductTotalPay();
				orderDetailRepository.save(orderdetail);
			}
			
			
			return true;
			
		}
		return false;
	
		
	}


	public OrderDetail update(Integer orderDetailNo,Integer boughtCount) {//修改訂單明細數量
		
		OrderDetail Detail=orderDetailRepository.findById(orderDetailNo).get();
		Detail.setOrderDetailNo(orderDetailNo);
		Detail.setProductBoughtCount(boughtCount);//只能編輯數量
		Detail.setDiscount();
		Detail.setProductTotalPay();
		
		return orderDetailRepository.save(Detail);
	}

	public boolean deleteById(Integer ordersNo) {// 依訂單編號刪除
		if (orderRepository.findById(ordersNo) != null) {
			orderRepository.deleteById(ordersNo);
			return true;
		}
		return false;
	}

	public OrderDetail findByOdNo(Integer OdNo) {// 依訂單編號查詢
		if (orderRepository.findById(OdNo) != null) {
			return orderDetailRepository.findById(OdNo).get();
		}
		return null;

	}



}
