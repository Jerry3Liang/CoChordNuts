package com.ispan.recordshop.cochordnuts.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ispan.recordshop.cochordnuts.dto.CartForOrdersDto;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;




@Service
public class OrdersServiceImpl  {
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MemberRepository memberRepo;
	
	public Integer findOrderCount(Integer num) {
		return orderRepository.findOrderCount(num);
	}
	public Integer findAllOrderCount() {
		return orderRepository.findAllOrderCount();
	}

	public List<Orders> selectAll(JSONObject obj) {//搜尋全部
		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
		String num =obj.isNull("num") ? "" : obj.getString("num");
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Orders> criteriaQuery = criteriaBuilder.createQuery(Orders.class);
		Root<Orders> table = criteriaQuery.from(Orders.class);
		criteriaQuery.select(table);
		List<Predicate> predicates = new ArrayList<>();
		if(num !=null && num !="") {
		    predicates.add(criteriaBuilder.like(table.get("orderNo").as(String.class), "%" + num + "%"));
		}
		
		if(predicates!=null && !predicates.isEmpty()) {
			Predicate[] array = predicates.toArray(new Predicate[0]);
			criteriaQuery = criteriaQuery.where(array);
		}
		criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get("orderNo")));
		
		TypedQuery<Orders> typedQuery = this.getSession().createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(rows);
		
		List<Orders> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
		
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
	
	public Orders update(Integer MemberNo,Orders orders) {//修改訂單狀態
		Integer orderNo= orders.getOrderNo();
		Orders origin = orderRepository.findById(orderNo).get();
		if(orders.getStatus().equals("訂單取消") && origin.getPaymentStatus().equals("已付款")) {			
			orders.setPaymentStatus("待退款");	
		}else if(orders.getStatus().equals("訂單取消") && origin.getPaymentStatus().equals("未付款")) {
			orders.setPaymentStatus("取消");
			
		}else  if(orders.getPaymentStatus()==null) {//如果前端沒傳paymentStatus，將舊資料放入orders中
			orders.setPaymentStatus(origin.getPaymentStatus());
		}
//		System.out.println(origin.getPayment().equals("現金") && orders.getStatus().equals("訂單完成"));
//		if(origin.getPayment().equals("現金") && orders.getStatus().equals("訂單完成") ) {
//			origin.setPaymentStatus("已付款");
//		}

		if(orderRepository.findById(orderNo)!=null) {
			Member m = memberRepository.findById(MemberNo).get();
			
			orders.setMemberNo(m);
			orders.setAddress();
			orders.setLastModifiedDate();
			
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
	
	public List<Orders> findBymemberNo(JSONObject obj) {// 依會員編號查詢訂單
		int memberNo=obj.isNull("memberNo") ? 0 : obj.getInt("memberNo");
		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
		String num =obj.isNull("num") ? "" : obj.getString("num");
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Orders> criteriaQuery = criteriaBuilder.createQuery(Orders.class);
//		from Orders
		Root<Orders> table = criteriaQuery.from(Orders.class);
		List<Predicate> predicates = new ArrayList<>();
		//where條件設定
		Predicate p = criteriaBuilder.equal(table.get("memberNo"), memberRepo.findById(memberNo).get());//依memberNo找到orders
		predicates.add(p);
		if(num !=null && num !="") {
		    predicates.add(criteriaBuilder.like(table.get("orderNo").as(String.class), "%" + num + "%"));
		}
		

		if(predicates!=null && !predicates.isEmpty()) {
			Predicate[] array = predicates.toArray(new Predicate[0]);//條件存入陣列
			criteriaQuery = criteriaQuery.where(array);//加到where中
		}
		criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get("orderNo")));
		TypedQuery<Orders> typedQuery = this.getSession().createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(rows);
		
		List<Orders> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}

	}
	public Integer findfindBymemberNoCount(JSONObject obj) {
		int memberNo=obj.isNull("memberNo") ? 0 : obj.getInt("memberNo");
		System.out.println(memberNo);
		return orderRepository.findMemberByMemberNoCount(memberNo);
	}
	
	public Integer findMemberByMemberNoAndOrderNoCount(JSONObject obj) {
		int memberNo=obj.isNull("memberNo") ? 0 : obj.getInt("memberNo");
		int OrderNo=obj.isNull("num") ? 0 : obj.getInt("num");
		
		return orderRepository.findMemberByMemberNoAndOrderNoCount(memberNo,OrderNo) ;
	}
	
//	public List<CartForOrdersDto> findCartByMember(Integer memberNo) {
//		List<CartForOrdersDto> cartArray = new ArrayList<>();
//		List<Map<String, Object>> results = orderRepository.findCartByMemberNo(memberNo);
//		if(results!=null) {			
//			for (Map<String, Object> row : results) {
//				CartForOrdersDto cart = new CartForOrdersDto();//將MAP取得的資料丟進新建的CartDto
//				cart.setCount((Integer)row.get("Count"));			
//				Integer productNo =(Integer) row.get("product_productNo");//取得Map中會員編號
//				Product product=productRepository.findById(productNo).get();//依會員編號取得product物件		
//				//將取得的product物件丟入Cart
//				cart.setDiscount(product.getDiscount());//折扣
//				cart.setUnitPrice(product.getUnitPrice());//單價
//				cart.setProductName(product.getProductName());//商品名稱
//				cart.setProductNo(productNo);//商品編號
//				cart.setTotal();
//				cartArray.add(cart);//cart存入陣列
//			}
//		
//		
//	}
//		return cartArray;
//}
	
	
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
	//依會員編號刪除Cart
	public void deleteCartByMemberNo(Integer memberNo) {
		orderRepository.deleteCartByMemberNo(memberNo);
	}
	
	//依商品編號(productNo)修改商品
	public Product updateProductByProductNo(Integer productNo, Integer Count ) {
		Product product = productRepository.findById(productNo).get();
		if(product!=null) {
			Integer stock = product.getStock()-Count;
			product.setStock(stock);
			return productRepository.save(product);
			
		}
		
		return null;
	}
}