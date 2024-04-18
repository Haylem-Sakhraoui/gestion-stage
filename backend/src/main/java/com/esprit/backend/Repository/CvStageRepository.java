package com.esprit.backend.Repository;


import com.esprit.backend.Entity.CvStage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvStageRepository extends CrudRepository<CvStage,Long> {
}
