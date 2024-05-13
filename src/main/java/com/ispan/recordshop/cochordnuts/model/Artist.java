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
@Table(name = "artist")
public class Artist {
	
	@OneToMany(
			mappedBy = "artist",
			cascade = CascadeType.ALL)
	private Set<Product> products;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer artistNo; //藝人編號
	
	private String artistName; //藝人名稱

	public Artist() {
	}
	
	

	@Override
	public String toString() {
		return "Artist [artistNo=" + artistNo + ", artistName=" + artistName + "]";
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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
	
}
