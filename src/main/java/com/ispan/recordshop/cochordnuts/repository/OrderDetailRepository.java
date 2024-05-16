package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>  {
	@Query(value = "SELECT * FROM Cart WHERE member_memberNo = :memberNo", nativeQuery = true)
	public List<Cart> findCartByMemberNo(@Param("memberNo")Integer memberNo);
	
//	@Query("")
//	public List<OrderDetail> findOrderDetailByOrderNo(@Param("OrderNo")Integer OrderNo);

	
}
