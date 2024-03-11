package com.esprit.backend.Services;

import com.esprit.backend.Entity.Offre;

import java.util.List;

public interface IOffreService {
    Offre AddStage(Offre offre);
    Offre getStageById(long idstage);
    List<Offre> getAllStages();
    void DeleteStage(long idstage);
    Offre updateStage(Offre offre);
}
