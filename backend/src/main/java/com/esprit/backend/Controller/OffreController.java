package com.esprit.backend.Controller;

import com.esprit.backend.Entity.CvStage;
import com.esprit.backend.Entity.Offre;
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
@RequestMapping("/offre")
public class OffreController {

  private final CvService cvService;
  private final IOffreService offreService;

  @PostMapping("/add")
  public Offre addOffre(@RequestBody Offre offre) {
    return offreService.AddStage(offre);
  }

  @GetMapping("/getAll")
  public List<Offre> getAllOffres() {
    return offreService.getAllStages();
  }

  @GetMapping("/get")
  public Offre getOffre(@RequestParam("id") long id) {
    return offreService.getStageById(id);
  }

  @GetMapping("/getByType")
  public List<Offre> getOffresByType(@RequestParam(required = false) String type) {
    if (type != null && !type.isEmpty()) {
      return offreService.getOffresByTypeStage(type);
    } else {
      return offreService.getAllStages();
    }
  }

  @DeleteMapping("/delete/{id}")
  public void deleteOffre(@PathVariable("id") long id) {
    offreService.DeleteStage(id);
  }

  @PutMapping("/update")
  public Offre updateOffre(@RequestBody Offre offre) {
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

  @PostMapping("/{id}/like")
  public void likeOffre(@PathVariable("id") long id) {
    offreService.likeStage(id);
  }

  @PostMapping("/{id}/dislike")
  public void dislikeOffre(@PathVariable("id") long id) {
    offreService.dislikeStage(id);
  }
}
