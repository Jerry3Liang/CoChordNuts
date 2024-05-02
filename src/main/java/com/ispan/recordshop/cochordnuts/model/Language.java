package com.ispan.recordshop.cochordnuts.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "language")
public class Language {
	
//	table建好再打開
//	@OneToMany(
//			mappedBy = "language",
//			cascade = CascadeType.ALL)
//	private Set<Product> products;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer languageNo; //語言類型編號
	
	private String languageType; //語言類型描述

	public Language() {
	}

	public Integer getLanguageNo() {
		return languageNo;
	}

	public void setLanguageNo(Integer languageNo) {
		this.languageNo = languageNo;
	}

	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}
	
	

}
