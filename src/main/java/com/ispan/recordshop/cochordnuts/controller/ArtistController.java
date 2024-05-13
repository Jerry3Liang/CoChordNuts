package com.ispan.recordshop.cochordnuts.controller;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> parent of 15584fd (Product 多條件查詢 & Artist依名稱查詢)
=======
import java.util.List;

import org.json.JSONObject;
>>>>>>> Product類型功能
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
<<<<<<< HEAD
=======
	@GetMapping("/product/artist/find")
	public List<Artist> findAll(){
		return artistService.findAll();
	}
	
>>>>>>> parent of 15584fd (Product 多條件查詢 & Artist依名稱查詢)
=======
	@GetMapping("/product/artist/find")
	public String findAll(){
//		return artistService.findAll();
		List<Artist> result = artistService.findAll();
		return result.toString();
	}
	
	@PostMapping("/product/artist/findByName")
	public String insert(@RequestBody String obj) {
		JSONObject json = new JSONObject(obj);
		String artistName = json.isNull("artistName")? null : json.getString("artistName");
		return artistService.findByName(artistName).toString();
	}
	
	
>>>>>>> Product類型功能
	
}
