package com.ispan.recordshop.cochordnuts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Favorite;
import com.ispan.recordshop.cochordnuts.repository.FavoriteRepository;

@Service
public class FavoriteService {
	
	@Autowired
	private FavoriteRepository favoriteRepo;
	
	public List<Integer> findStyleNoByMemberId(Integer memberId) {
		
		List<Integer> interger = new ArrayList<>();
		List<Favorite> favorites = favoriteRepo.findStyleNoByMemberId(memberId);
		if(favorites != null) {
			for(Favorite favorite : favorites) {
				interger.add(favorite.getFavoriteId().getProductStyleId());
			}
			
			return interger;
		}
		
		return null;
		
	}
	
	

}
