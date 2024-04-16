package com.esprit.backend.Services;

import com.esprit.backend.Entity.Offre;
import com.esprit.backend.Entity.User;

import java.util.List;
import java.util.Set;

public interface IOffreService {
    Offre AddStage(Offre offre);
    Offre getStageById(long idstage);
    List<Offre> getAllStages();
    void DeleteStage(long idstage);
    Offre updateStage(Offre offre);
  public void likeStage(long idstage, User user);
  public void dislikeStage(long idstage, User user);
  public List<Offre> getOffresByTypeStage(String typeStage);
  public List<Offre> matchCvToOffres(Set<String> cvSkills);
}
