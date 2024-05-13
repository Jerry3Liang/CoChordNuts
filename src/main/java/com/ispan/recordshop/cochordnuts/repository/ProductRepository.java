package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("from Product")
	Page<Product> findAllByPages(Pageable pageable);
	
	@Query("from Product where isBest = :n")
	List<Product> findIsBest(@Param("n") Integer isBest);
	
	@Query("from Product where isDiscount = :n")
	List<Product> findIsDiscount(@Param("n") Integer isDiscount);
	
	@Query("from Product where isPreorder = :n")
	List<Product> findIsPreorder(@Param("n") Integer isPreorder);
	

}
