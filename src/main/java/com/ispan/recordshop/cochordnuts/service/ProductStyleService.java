package com.ispan.recordshop.cochordnuts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.ArtistDTO;
import com.ispan.recordshop.cochordnuts.dto.StyleDTO;
import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.model.ProductStyle;
import com.ispan.recordshop.cochordnuts.repository.ProductStyleRepository;

@Service
public class ProductStyleService {
	
	@Autowired
	private ProductStyleRepository prodStyleRepo;
	
	public ProductStyle insert(ProductStyle style) {
		return prodStyleRepo.save(style);
	}
	
	public List<StyleDTO> findAll(){
		List<ProductStyle> types = prodStyleRepo.findAll();
		List<StyleDTO> result = new ArrayList<>();
		StyleDTO styleDTO = null;
		for(ProductStyle type : types) {
			styleDTO = new StyleDTO();
			styleDTO.setStyleNo(type.getStyleNo());
			styleDTO.setStyleType(type.getStyleType());
			result.add(styleDTO);
		}
		return result;
	}
	
	public ProductStyle findById(Integer id){
		return prodStyleRepo.findById(id).get();
	}
	
	public List<ProductStyle> findByName(String name) {
		return prodStyleRepo.findByNameLike(name);
	}
	
	

}
