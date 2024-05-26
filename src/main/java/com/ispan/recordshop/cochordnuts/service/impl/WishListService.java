package com.ispan.recordshop.cochordnuts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.WishList;
import com.ispan.recordshop.cochordnuts.repository.WishListRepository;

@Service
public class WishListService {
	
	@Autowired
	private WishListRepository wishListRepo;

	public void addWishList(WishList wishlist) {
		wishListRepo.save(wishlist);
	}
	
	
	
}
