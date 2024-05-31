package com.ispan.recordshop.cochordnuts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.recordshop.cochordnuts.model.Favorite;
import com.ispan.recordshop.cochordnuts.model.FavoriteId;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
	
	@Query("from Favorite where favoriteId.memberId = :n")
	List<Favorite> findStyleNoByMemberId(@Param("n") Integer memberId);

}
