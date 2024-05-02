package com.ispan.recordshop.cochordnuts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository ProductRepo;
	
	
}
