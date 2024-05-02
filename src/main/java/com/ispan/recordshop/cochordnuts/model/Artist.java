package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer artistNo; //藝人編號
	
	private String artistName; //藝人名稱

	public Artist() {
	}

	public Integer getArtistNo() {
		return artistNo;
	}

	public void setArtistNo(Integer artistNo) {
		this.artistNo = artistNo;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	
	
	
}
