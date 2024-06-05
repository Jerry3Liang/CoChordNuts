package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.MusicYear;

public interface MusicYearRepository extends JpaRepository<MusicYear, Integer> {

	@Query("from MusicYear where generation like %:generation%")
	public List<MusicYear> findByMusicYearLike(@Param("generation") String generation);

}
