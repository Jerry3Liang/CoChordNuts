package com.ispan.recordshop.cochordnuts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.MusicYear;
import com.ispan.recordshop.cochordnuts.model.ProductStyle;
import com.ispan.recordshop.cochordnuts.repository.MusicYearRepository;

@Service
public class MusicYearService {
	
	@Autowired
	private MusicYearRepository musicYearRepo;
	
	public MusicYear insert(MusicYear year) {
		return musicYearRepo.save(year);
	}
	
	public List<MusicYear> findAll(){
		return musicYearRepo.findAll();
	}
	
	public MusicYear findById(Integer id){
		return musicYearRepo.findById(id).get();
	}
	
	

}
