package com.esprit.backend.Controller;

import com.esprit.backend.Entity.Offre;
import com.esprit.backend.Services.IOffreService;
import io.swagger.annotations.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("offre")
public class OffreController {


  IOffreService offreService;

  @PostMapping("add")
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

  @DeleteMapping("delete/{idstage}")
  public void DeleteStage(@PathVariable("idstage") long idstage) {
    offreService.DeleteStage(idstage);
  }

  // Order 5
  @PutMapping("update")
  public Offre updateStage(@RequestBody Offre offre) {
    return offreService.updateStage(offre);
  }

  @PostMapping("{idstage}/like")
  public void likeStage(@PathVariable("idstage") long idstage) {
    offreService.likeStage(idstage);
  }

  @PostMapping("{idstage}/dislike")
  public void dislikeStage(@PathVariable("idstage") long idstage) {
    offreService.dislikeStage(idstage);
  }
}
