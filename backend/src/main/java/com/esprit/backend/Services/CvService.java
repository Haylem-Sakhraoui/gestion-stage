package com.esprit.backend.Services;

import com.esprit.backend.Entity.CvStage;
import com.esprit.backend.Repository.CvStageRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CvService implements ICVService{
  CvStageRepository CvRepo;

  @Override
  public CvStage getCvById(long idCv) {
    return CvRepo.findById(idCv).get();
  }

  @Override
  public CvStage AddCV(CvStage cvStage) {
    return CvRepo.save(cvStage) ;
  }
}
