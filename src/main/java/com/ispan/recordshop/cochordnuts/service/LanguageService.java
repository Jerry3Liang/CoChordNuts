package com.ispan.recordshop.cochordnuts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Language;
import com.ispan.recordshop.cochordnuts.repository.LanguageRepository;

@Service
public class LanguageService {
	
	@Autowired
	private LanguageRepository languageRepo;
	
	public Language insert(Language language) {
		return languageRepo.save(language);
	}
	
	public List<Language> findAll(){
		return languageRepo.findAll();
	}
	

}
