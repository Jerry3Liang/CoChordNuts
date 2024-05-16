package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.CartId;

public interface CartRepository extends JpaRepository<Cart, CartId> {

	@Query("select c from Cart c where c.cartId.memberId = :memberId ")
    public List<Cart> findMemberCartProducts(@Param("memberId") Integer loggedInUser);
	
	@Query("select c from Cart c where c.cartId.memberId = :x and c.cartId.productId = :y")
	public Cart findByMemberAndProducts(@Param("x") Integer loggedInUser, @Param("y") Integer cartProductId);
	
}
