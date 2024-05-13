package com.ispan.recordshop.cochordnuts.controller;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> parent of 15584fd (Product 多條件查詢 & Artist依名稱查詢)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.service.ArtistService;

@RestController
@CrossOrigin
public class ArtistController {

	@Autowired
	private ArtistService artistService;
	
	@PostMapping("/product/artist/add")
	public Artist insert(@RequestBody Artist artist) {
		return artistService.insert(artist);
	}
	
<<<<<<< HEAD
=======
	@GetMapping("/product/artist/find")
	public List<Artist> findAll(){
		return artistService.findAll();
	}
	
>>>>>>> parent of 15584fd (Product 多條件查詢 & Artist依名稱查詢)
	
}
