package com.ispan.recordshop.cochordnuts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.recordshop.cochordnuts.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("from Product")
	Page<Product> findAllByPages(Pageable pageable);

}
