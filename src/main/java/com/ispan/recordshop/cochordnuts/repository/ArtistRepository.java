package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
	
	@Query("from Artist where artistName like %:name%")
	public List<Artist> findByNameLike(@Param("name") String name);

}
