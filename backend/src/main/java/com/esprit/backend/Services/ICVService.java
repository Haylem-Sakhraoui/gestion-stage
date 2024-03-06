package com.esprit.backend.Services;

import com.esprit.backend.Entity.CvStage;

public interface ICVService {
  CvStage getCvById(long idCv);

  CvStage AddCV(CvStage cvStage);
}
