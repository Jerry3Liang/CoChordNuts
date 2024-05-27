package com.ispan.recordshop.cochordnuts.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.dto.ProductDTO;
import com.ispan.recordshop.cochordnuts.model.WishList;
import com.ispan.recordshop.cochordnuts.service.WishListService;

@RestController
@CrossOrigin
public class WishListController {
	
	@Autowired
	private WishListService wishListService;
	
	@PostMapping("/wishlist/add")  
	public ResponseEntity<?> addWishList(@RequestBody String obj) {
		JSONObject json = new JSONObject(obj);
		WishList result = wishListService.addWishList(json);
		if(result != null) {
			return ResponseEntity.ok(result.toString());
		} else {
			return ResponseEntity.notFound().build();
		}
		
//		return ResponseEntity.created(URI.create(uri))
//		.contentType(MediaType.APPLICATION_JSON)
//		.body(newProd);
		
	}
	
	@GetMapping("/wishlist/find/{memberId}")
	public List<ProductDTO> findWishList(@PathVariable Integer memberId){
		return wishListService.findWishList(memberId);
		
	}
	
	// 刪除
	@DeleteMapping("/wishlist/remove/{memberId}/{productNo}")
	public ResponseEntity<Void> remove(@PathVariable Integer memberId, @PathVariable Integer productNo){
//		JSONObject json = new JSONObject(obj);
//		Integer memberId = json.isNull("memberId")? null : json.getInt("memberId");
//		Integer productNo = json.isNull("productNo")? null : json.getInt("productNo");
		if(memberId != null && memberId != 0 && productNo != null && productNo != 0) {
			boolean result = wishListService.removeWishList(memberId, productNo);
			if(result) {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	
	
	
	
	

}
