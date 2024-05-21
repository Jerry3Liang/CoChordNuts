package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Language;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.model.ProductStyle;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("from Product")
	Page<Product> findAllByPages(Pageable pageable);
	
	@Query("from Product where isBest = :n")
	List<Product> findIsBest(@Param("n") Integer isBest);
	
	@Query("from Product where isDiscount = :n")
	List<Product> findIsDiscount(@Param("n") Integer isDiscount);
	
	@Query("from Product where isPreorder = :n")
	List<Product> findIsPreorder(@Param("n") Integer isPreorder);
	
	@Query("from Product where language = :n")
	List<Product> findByLanguage(@Param("n") Language language);
	
	@Query("from Product where productStyle = :n")
	List<Product> findByStyle(@Param("n") ProductStyle style);
	
	

}
