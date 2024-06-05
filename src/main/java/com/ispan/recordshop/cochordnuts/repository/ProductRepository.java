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
	
	@Query("from Product where isBest = :n and productStatus = :m")
	List<Product> findIsBest(@Param("n") Integer isBest, @Param("m") Integer productStatus);
	
	@Query("from Product where isDiscount = :n and productStatus = :m")
	List<Product> findIsDiscount(@Param("n") Integer isDiscount, @Param("m") Integer productStatus);
	
	@Query("from Product where isPreorder = :n and productStatus = :m")
	List<Product> findIsPreorder(@Param("n") Integer isPreorder, @Param("m") Integer productStatus);
	
	@Query("from Product where language = :n and productStatus = :m")
	Page<Product> findByLanguageInf(@Param("n") Language language, @Param("m") Integer productStatus, Pageable pageable);
	
	@Query("from Product where language = :n and productStatus = :m")
	List<Product> findByLanguage(@Param("n") Language language, @Param("m") Integer productStatus);
	
	@Query("from Product where productStyle = :n and productStatus = :m")
	Page<Product> findByStyleInf(@Param("n") ProductStyle style, @Param("m") Integer productStatus, Pageable pageable);
	
	@Query("from Product where productStyle = :n and productStatus = :m")
	List<Product> findByStyle(@Param("n") ProductStyle style, @Param("m") Integer productStatus);
	
	

}
