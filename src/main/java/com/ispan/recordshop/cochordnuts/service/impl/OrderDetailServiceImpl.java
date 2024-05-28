package com.ispan.recordshop.cochordnuts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.CartForOrdersDto;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderDetailRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;

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

	public List<OrderDetail> selectAll() {

		return orderDetailRepository.findAll();
	}

	
	public OrderDetail insert(CartForOrdersDto cartDto) {// 新增OrderDetail
		Orders od = orderRepository.findById(cartDto.getOrderNo()).get();//依OrderNo找出orders
		Product pd=productRepository.findById(cartDto.getProductNo()).get();//依productNo找出product
		OrderDetail orderdetail=new OrderDetail();
		orderdetail.setOrderNo(od);
		orderdetail.setProductNo(pd);
		orderdetail.setDiscount();//取得商品折扣
		orderdetail.setProductBoughtCount(cartDto.getCount());//從購物車抓出購買數量
		orderdetail.setProductTotalPay();//計算單一品項小計
		if(orderdetail!=null) {
			return orderDetailRepository.save(orderdetail);
		}
		return null;
		

		
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

	public List<OrderDetail> findByOdNo(Integer OdNo) {// 依訂單編號查詢
		if (orderRepository.findById(OdNo) != null) {
			return orderDetailRepository.findOrderDetailByOrderNo(OdNo);
		}
		return null;

	}

}
