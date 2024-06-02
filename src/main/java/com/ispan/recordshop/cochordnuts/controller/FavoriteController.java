package com.ispan.recordshop.cochordnuts.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.dto.ProductDTO;
import com.ispan.recordshop.cochordnuts.service.FavoriteService;
import com.ispan.recordshop.cochordnuts.service.ProductService;

@RestController
@CrossOrigin
public class FavoriteController {
	
	@Autowired
	private FavoriteService favoriteService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/favoriteFind/{memberId}")
	public List<ProductDTO> favoriteFind(@PathVariable Integer memberId) {
		List<Integer> result = favoriteService.findStyleNoByMemberId(memberId);
//		System.out.println(result);
		
		if(result.isEmpty()) {
			return null;
		}
		JSONObject json = new JSONObject();
		json.put("style", result);
//		System.out.println(productService.search(json));
		return productService.search(json);
		
		
		
	}
	

}
