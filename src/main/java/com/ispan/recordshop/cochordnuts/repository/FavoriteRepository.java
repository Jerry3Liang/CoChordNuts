package com.ispan.recordshop.cochordnuts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.recordshop.cochordnuts.model.Favorite;
import com.ispan.recordshop.cochordnuts.model.FavoriteId;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

}
