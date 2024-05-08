package com.ispan.recordshop.cochordnuts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.CartId;

public interface CartRepository extends JpaRepository<Cart, CartId> {

}
