package com.ispan.recordshop.cochordnuts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.recordshop.cochordnuts.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>  {

}
