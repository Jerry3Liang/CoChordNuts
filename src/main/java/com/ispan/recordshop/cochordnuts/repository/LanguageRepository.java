package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
	
	@Query("from Language where languageType like %:language%")
	public List<Language> findByLanguageLike(@Param("language") String language);

}
