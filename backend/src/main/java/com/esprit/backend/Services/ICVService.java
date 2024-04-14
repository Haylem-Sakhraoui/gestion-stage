package com.esprit.backend.Services;

import com.esprit.backend.Entity.CvStage;

import java.util.List;

public interface ICVService {
  CvStage getCvById(long idCv);

  CvStage AddCV(CvStage cvStage);
  public List<CvStage> getAllCVs();



}
