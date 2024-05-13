package com.ispan.recordshop.cochordnuts.controller;

import java.net.URI;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ProductService productService;

	//新增產品
	@PostMapping("/products/create")
	public ResponseEntity<?> createProduct(@RequestBody Product product) {
		if(product != null) {
			Product result = productService.findById(product.getProductNo());
			if(result == null) {
				Product newProd = productService.insert(product);
				if(newProd != null) {
					String uri = "http://localhost:8080/products/create"+product.getProductNo();
					return ResponseEntity.created(URI.create(uri))
							.contentType(MediaType.APPLICATION_JSON)
							.body(newProd);
				}
			}
		}
		return ResponseEntity.noContent().build();
	}
	
	//商品修改
	@PutMapping("/products/modify/{id}")
	public ResponseEntity<?> modifyProduct(@PathVariable Integer id, @RequestBody Product product) {
		if(product != null && product.getProductNo() != null && product.getProductNo() != 0) {
			Product result = productService.findById(product.getProductNo());
			if(result != null) {
				Product newProd = productService.modify(product);
				if(newProd != null) {
					String uri = "http://localhost:8080/products/modify"+product.getProductNo();
					return ResponseEntity.created(URI.create(uri))
							.contentType(MediaType.APPLICATION_JSON)
							.body(newProd);
				}
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//多條件查詢
	@PostMapping("/products/search")
	public ResponseEntity<?> searchByCondition(@RequestBody String obj) {
		JSONObject json = new JSONObject(obj);
		System.out.println(json.toString());
		List<Product> result = productService.search(json);
		if(result != null && !result.isEmpty()) {
//			System.out.println(result.toString());
			return ResponseEntity.ok(result.toString());
		} else {
			return ResponseEntity.notFound().build();
		}
	
//		if(!result.isEmpty()) {
////			System.out.println(result.toString());
////			System.out.println(result.size());
//			return result.toString();
//		} else {
////			System.out.println("notFound");
//			return null;
//		}
		
	}
	
	// 查詢全部
//	public ResponseEntity<?> findAll(){
//		
//	}
	
	
	
	
	
	
	
	


}
