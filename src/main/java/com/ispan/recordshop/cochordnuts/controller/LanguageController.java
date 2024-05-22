package com.ispan.recordshop.cochordnuts.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.dto.LanguageDTO;
import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.model.Language;
import com.ispan.recordshop.cochordnuts.service.LanguageService;

@RestController
@CrossOrigin
public class LanguageController {

	@Autowired
	private LanguageService languageService;
	
	@PostMapping("/product/language/add")
	public Language insert(@RequestBody Language language) {
		return languageService.insert(language);
	}
	
	@GetMapping("/product/language/find")
	public List<LanguageDTO> findAll(){
		return languageService.findAll();
	}
	
	@PostMapping("/product/language/findByLanguage")
	public String insert(@RequestBody String obj) {
		JSONObject json = new JSONObject(obj);
		String languageType = json.isNull("languageType") ? null : json.getString("languageType");
		return languageService.findByLanguage(languageType).toString();
	}
	
	
	
}
