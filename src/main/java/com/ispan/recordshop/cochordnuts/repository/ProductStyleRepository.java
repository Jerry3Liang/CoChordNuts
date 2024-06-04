package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.model.ProductStyle;

public interface ProductStyleRepository extends JpaRepository<ProductStyle, Integer> {
	
	@Query("from ProductStyle where styleType like %:name%")
	public List<ProductStyle> findByNameLike(@Param("name") String name);

}
