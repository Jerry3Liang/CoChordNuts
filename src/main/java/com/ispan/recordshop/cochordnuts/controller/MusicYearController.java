package com.ispan.recordshop.cochordnuts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.dto.MusicYearDTO;
import com.ispan.recordshop.cochordnuts.model.MusicYear;
import com.ispan.recordshop.cochordnuts.service.MusicYearService;

@RestController
@CrossOrigin
public class MusicYearController {
	
	@Autowired
	private MusicYearService musicYearService;
	
	@PostMapping("/product/musicYear/add")
	public MusicYear insert(@RequestBody MusicYear year) {
		return musicYearService.insert(year);
	}
	
	@GetMapping("/product/musicYear/find")
	public List<MusicYearDTO> findAll(){
		return musicYearService.findAll();
	}
	
	

}
