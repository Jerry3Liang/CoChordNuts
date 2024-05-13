package com.ispan.recordshop.cochordnuts.service;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> parent of 15584fd (Product 多條件查詢 & Artist依名稱查詢)
=======
import java.util.List;

import org.hibernate.Session;
>>>>>>> Product類型功能
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.repository.ArtistRepository;

import jakarta.persistence.PersistenceContext;

@Service
public class ArtistService {
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	@Autowired
	private ArtistRepository artistRepository;
	
	public Artist insert(Artist artist) {
		return artistRepository.save(artist);
	}
	
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> Product類型功能
	public List<Artist> findAll(){
		return artistRepository.findAll();
	}
	
<<<<<<< HEAD
	
	
>>>>>>> parent of 15584fd (Product 多條件查詢 & Artist依名稱查詢)
=======
	public List<Artist> findByName(String name){
		
		return artistRepository.findByNameLike(name);
	}
	
	
	
	
>>>>>>> Product類型功能

}
