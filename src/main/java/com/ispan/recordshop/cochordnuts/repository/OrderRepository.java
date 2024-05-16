package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

	@Query(value="SELECT *FROM Orders  WHERE memberNo = :memberNo" , nativeQuery = true)
	public List<Orders> findBymemberNo(@Param("memberNo") Integer memberNo);
	
	@Query(value = "SELECT * FROM Cart WHERE member_memberNo = :memberNo", nativeQuery = true)
	public List<Map<String, Object>> findCartByMemberNo(@Param("memberNo")Integer memberNo);
	
	@Query(value="Select * from Member where memberNo = :memberNo",nativeQuery = true)
	public List<Map<String, Object>> findMemberByMemberNo(@Param("memberNo") Integer memberNo);
		
}
