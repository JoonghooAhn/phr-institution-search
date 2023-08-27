package com.cenacle.edge.repository;

import com.cenacle.edge.model.entity.Clinic;
import com.cenacle.edge.support.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends BaseRepository<Clinic, String> {

}
