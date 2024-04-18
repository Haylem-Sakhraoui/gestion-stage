package com.esprit.backend.Services;

import com.esprit.backend.Entity.Offre;


import java.util.List;
import java.util.Set;

public interface IOffreService {
    Offre AddStage(Offre offre);
    Offre getStageById(long idstage);
    List<Offre> getAllStages();
    void DeleteStage(long idstage);
    Offre updateStage(Offre offre);

  public List<Offre> getOffresByTypeStage(String typeStage);
  public List<Offre> matchCvToOffres(Set<String> cvSkills);
  public void likeStage(long idstage);
  public void dislikeStage(long idstage);
}
