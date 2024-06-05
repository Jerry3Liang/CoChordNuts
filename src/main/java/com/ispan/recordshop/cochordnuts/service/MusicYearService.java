package com.ispan.recordshop.cochordnuts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.MusicYearDTO;
import com.ispan.recordshop.cochordnuts.dto.StyleDTO;
import com.ispan.recordshop.cochordnuts.model.Language;
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
	
	public List<MusicYearDTO> findAll(){
		List<MusicYear> musics = musicYearRepo.findAll();
		List<MusicYearDTO> result = new ArrayList<>();
		MusicYearDTO musicYearDTO = null;
		for(MusicYear music : musics) {
			musicYearDTO = new MusicYearDTO();
			musicYearDTO.setMusicYearNo(music.getMusicYearNo());
			musicYearDTO.setGeneration(music.getGeneration());
			result.add(musicYearDTO);
		}
		return result;
	}
	
	public MusicYear findById(Integer id){
		return musicYearRepo.findById(id).get();
	}
	
	
	public List<MusicYear> findByMusicYear(String generation) {
		return musicYearRepo.findByMusicYearLike(generation);
	}
	

}
