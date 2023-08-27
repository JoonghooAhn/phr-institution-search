package com.cenacle.edge.repository;

import com.cenacle.edge.model.entity.ClinicFavorite;
import com.cenacle.edge.support.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicFavoriteRepository extends BaseRepository<ClinicFavorite, String> {
}
