package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.recordshop.cochordnuts.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

	@Query(value="SELECT *FROM Orders  WHERE memberNo = :memberNo" , nativeQuery = true)
	public List<Orders> findBymemberNo(@Param("memberNo") Integer memberNo);
	
	@Query(value = "SELECT * FROM Cart WHERE member_memberNo = :memberNo", nativeQuery = true)
	public List<Map<String, Object>> findCartByMemberNo(@Param("memberNo")Integer memberNo);
	
//	@Query(value="Select * from Member where memberNo = :memberNo",nativeQuery = true)
//	public List<Map<String, Object>> findMemberByMemberNo(@Param("memberNo") Integer memberNo);
	@Query(value="Select count(*) from Orders where memberNo = :memberNo",nativeQuery = true)
	public Integer findMemberByMemberNoCount(@Param("memberNo") Integer memberNo);
	
	//訂單編號模糊搜尋筆數-依會員搜尋訂單後
	@Query(value="SELECT COUNT(*) FROM Orders WHERE memberNo = :memberNo AND orderNo LIKE %:orderNo%", nativeQuery = true)
	public Integer findMemberByMemberNoAndOrderNoCount(@Param("memberNo") Integer memberNo, @Param("orderNo") Integer orderNo);
	
	@Query(value="SELECT COUNT(*) FROM Orders WHERE orderNo LIKE CONCAT('%', :orderNo, '%')", nativeQuery = true)
	public Integer findOrderCount(@Param("orderNo") Integer orderNo);
	
	@Query(value="SELECT COUNT(*) FROM Orders ", nativeQuery = true)
	public Integer findAllOrderCount();
	
	@Transactional
    @Modifying
	@Query(value="Delete Cart where member_memberNo=:memberNo ", nativeQuery = true)
	public void deleteCartByMemberNo(@Param("memberNo") Integer memberNo);
	
	
}
