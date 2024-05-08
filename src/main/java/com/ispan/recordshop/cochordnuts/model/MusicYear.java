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
@Table(name = "music_year")
public class MusicYear {
	
	@OneToMany(
			mappedBy = "musicYear",
			cascade = CascadeType.ALL)
	private Set<Product> products;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer musicYearNo; //年代編號
	
	private String generation; //年代

	public MusicYear() {
	}

	public Integer getMusicYearNo() {
		return musicYearNo;
	}

	public void setMusicYearNo(Integer musicYearNo) {
		this.musicYearNo = musicYearNo;
	}

	public String getGeneration() {
		return generation;
	}

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	
	
	
	
}
