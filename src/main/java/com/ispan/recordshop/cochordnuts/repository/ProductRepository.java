package com.ispan.recordshop.cochordnuts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.recordshop.cochordnuts.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
