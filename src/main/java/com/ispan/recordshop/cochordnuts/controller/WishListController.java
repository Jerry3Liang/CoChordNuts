package com.ispan.recordshop.cochordnuts.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WishListController {
	
	@PostMapping("/wishlist/add")
	public void addWishList() {
		
	}

}
