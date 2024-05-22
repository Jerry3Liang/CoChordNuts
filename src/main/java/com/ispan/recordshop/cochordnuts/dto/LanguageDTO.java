package com.ispan.recordshop.cochordnuts.dto;

import lombok.Data;

@Data
public class LanguageDTO {
	
	private Integer languageNo; //語言類型編號
	
	private String languageType; //語言類型描述

	public LanguageDTO() {
	}

}
