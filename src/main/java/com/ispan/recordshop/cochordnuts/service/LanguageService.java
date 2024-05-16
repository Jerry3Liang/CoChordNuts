package com.ispan.recordshop.cochordnuts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.LanguageDTO;
import com.ispan.recordshop.cochordnuts.dto.StyleDTO;
import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.model.Language;
import com.ispan.recordshop.cochordnuts.model.ProductStyle;
import com.ispan.recordshop.cochordnuts.repository.LanguageRepository;

@Service
public class LanguageService {
	
	@Autowired
	private LanguageRepository languageRepo;
	
	public Language insert(Language language) {
		return languageRepo.save(language);
	}
	
	public List<LanguageDTO> findAll(){
		List<Language> languages = languageRepo.findAll();
		List<LanguageDTO> result = new ArrayList<>();
		LanguageDTO languageDTO = null;
		for(Language language : languages) {
			languageDTO = new LanguageDTO();
			languageDTO.setLanguageNo(language.getLanguageNo());
			languageDTO.setLanguageType(language.getLanguageType());
			result.add(languageDTO);
		}
		return result;
	}
	
	public List<Language> findByLanguage(String language) {
		return languageRepo.findByLanguageLike(language);
	}
	
	public Language findById(Integer id){
		return languageRepo.findById(id).get();
	}
	

}
