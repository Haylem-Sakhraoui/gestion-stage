package com.esprit.backend.Controller;

import com.esprit.backend.Entity.CvStage;
import com.esprit.backend.Entity.Offre;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Services.CvService;
import com.esprit.backend.Services.IOffreService;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("offre")
public class OffreController {

  CvService cvService;
  IOffreService offreService;

  @PostMapping("addStage")
  public Offre AddOffre(@RequestBody Offre offre) {
    return offreService.AddStage(offre);
  }

  // Order 2
  @GetMapping("getAll")
  public List<Offre> GetALLStage() {
    return offreService.getAllStages();
  }

  @GetMapping("get")
  public Offre getstage(@RequestParam("idstage") long idstage) {
    return offreService.getStageById(idstage);
  }
  @GetMapping("gettype")
  public List<Offre> getOffres(@RequestParam(required = false) String  typeStage) {
    if ( typeStage != null && ! typeStage.isEmpty()) {
      return offreService.getOffresByTypeStage( typeStage);
    } else {
      return offreService.getAllStages();
    }
  }
  @DeleteMapping("delete/{idstage}")
  public void DeleteStage(@PathVariable("idstage") long idstage) {
    offreService.DeleteStage(idstage);
  }

  // Order 5
  @PutMapping("update")
  public Offre updateStage(@RequestBody Offre offre) {
    return offreService.updateStage(offre);
  }



  @GetMapping("/match/{cvId}")
  public ResponseEntity<List<Offre>> matchCvToOffres(@PathVariable Long cvId) {
    CvStage cvStage = cvService.getCvById(cvId);
    if (cvStage == null) {
      return ResponseEntity.notFound().build();
    }
    Set<String> cvSkills = cvStage.getSkills();
    List<Offre> matchedOffres = offreService.matchCvToOffres(cvSkills);
    return ResponseEntity.ok(matchedOffres);
  }
}



