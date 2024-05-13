package com.ispan.recordshop.cochordnuts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.ProductStyle;
import com.ispan.recordshop.cochordnuts.repository.ProductStyleRepository;

@Service
public class ProductStyleService {
	
	@Autowired
	private ProductStyleRepository prodStyleRepo;
	
	public ProductStyle insert(ProductStyle style) {
		return prodStyleRepo.save(style);
	}
	
	public List<ProductStyle> findAll(){
		return prodStyleRepo.findAll();
	}
	
	

}
