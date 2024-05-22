package com.ispan.recordshop.cochordnuts.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.ArtistDTO;
import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.model.ProductStyle;
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

	public List<ArtistDTO> findAll() {
		List<Artist> artists = artistRepository.findAll();
		List<ArtistDTO> result = new ArrayList<>();
		ArtistDTO artistDTO = null;
		for(Artist artist : artists) {
			artistDTO = new ArtistDTO();
			artistDTO.setArtistNo(artist.getArtistNo());
			artistDTO.setArtistName(artist.getArtistName());
			result.add(artistDTO);
		}
		return result;
	}

	public List<Artist> findByName(String name) {
		return artistRepository.findByNameLike(name);
	}
	
	public Artist findById(Integer id){
		return artistRepository.findById(id).get();
	}

}
