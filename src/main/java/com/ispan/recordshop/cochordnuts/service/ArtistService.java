package com.ispan.recordshop.cochordnuts.service;

import java.util.List;

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
	
	public List<Artist> findAll(){
		return artistRepository.findAll();
	}
	
	
	

}
