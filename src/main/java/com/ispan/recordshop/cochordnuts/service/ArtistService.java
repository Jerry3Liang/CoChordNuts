package com.ispan.recordshop.cochordnuts.service;

import java.util.List;

import org.hibernate.Session;
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
	
	public List<Artist> findAll(){
		return artistRepository.findAll();
	}
	
	public List<Artist> findByName(String name){
		
		return artistRepository.findByNameLike(name);
	}
	
	
	
	

}
