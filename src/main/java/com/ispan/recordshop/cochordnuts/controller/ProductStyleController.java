package com.ispan.recordshop.cochordnuts.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.ProductStyle;
import com.ispan.recordshop.cochordnuts.service.ProductStyleService;

@RestController
@CrossOrigin
public class ProductStyleController {
	
	private ProductStyleService prodStyleService;
	
	@PostMapping("/product/style/add")
	public ProductStyle insert(@RequestBody ProductStyle style) {
		return prodStyleService.insert(style);
	}
	
	@GetMapping("/product/style/find")
	public List<ProductStyle> findAll(){
		return prodStyleService.findAll();
	}
	

}
