package com.ispan.recordshop.cochordnuts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.recordshop.cochordnuts.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
