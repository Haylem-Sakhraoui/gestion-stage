package com.esprit.backend.Services;

import com.esprit.backend.Entity.Offre;
import com.esprit.backend.Repository.OffreRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OffreService implements IOffreService{
    OffreRepository offreRepo;
    @Override
    public Offre AddStage(Offre offre) {
        return offreRepo.save(offre);
    }

    @Override
    public Offre getStageById(long idstage) {
        return offreRepo.findById(idstage).get();
    }

    @Override
    public List<Offre> getAllStages() {
        return(List<Offre>) offreRepo.findAll();
    }

    @Override
    public void DeleteStage(long idstage) {
     offreRepo.deleteById(idstage);

    }

    @Override
    public Offre updateStage(Offre offre) {
        return offreRepo.save(offre);
    }
  @Override
  public void likeStage(long idstage) {
   Offre offre = offreRepo.findById(idstage).orElse(null);
    if (offre!= null) {
      offre.setLikes(offre.getLikes() + 1);
      offreRepo.save(offre);
    }


  }

  @Override
  public void dislikeStage(long idstage) {
    Offre offre = offreRepo.findById(idstage).orElse(null);
    if (offre != null) {
      offre.setDislikes(offre.getDislikes() + 1);
      offreRepo.save(offre);
    }
  }

  @Override
  public List<Offre> getOffresByTypeStage(String typeStage) {
    return offreRepo.findByTypeStageContainingIgnoreCase(typeStage);
  }
  public List<Offre> matchCvToOffres(Set<String> cvSkills) {
    Iterable<Offre> allOffresIterable = offreRepo.findAll();
    List<Offre> allOffres = StreamSupport.stream(allOffresIterable.spliterator(), false)
      .collect(Collectors.toList());
    List<Offre> matchedOffres = new ArrayList<>();

    for (Offre offre : allOffres) {
      Set<String> intersection = new HashSet<>(offre.getCompetence());
      intersection.retainAll(cvSkills);

      if (!intersection.isEmpty()) {
        matchedOffres.add(offre);
      }
    }
    return matchedOffres;
  }
}

