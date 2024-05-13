package com.ispan.recordshop.cochordnuts.service;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> parent of 15584fd (Product 多條件查詢 & Artist依名稱查詢)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.repository.ArtistRepository;

@Service
public class ArtistService {
	
	@Autowired
	private ArtistRepository artistRepository;
	
	public Artist insert(Artist artist) {
		return artistRepository.save(artist);
	}
	
<<<<<<< HEAD
=======
	public List<Artist> findAll(){
		return artistRepository.findAll();
	}
	
	
	
>>>>>>> parent of 15584fd (Product 多條件查詢 & Artist依名稱查詢)

}
