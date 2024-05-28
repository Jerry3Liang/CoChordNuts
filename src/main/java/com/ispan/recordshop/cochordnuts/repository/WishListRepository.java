package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.WishList;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
	
	@Query("from WishList where member.memberNo = :n")
	List<WishList> findByMemberId(@Param("n") Integer memberId);

	@Query("from WishList where member.memberNo = :n and product.productNo = :m")
	WishList findByMemberIdAndproductNo(@Param("n") Integer memberId, @Param("m") Integer productNo);

}
