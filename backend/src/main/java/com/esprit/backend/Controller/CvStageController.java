package com.esprit.backend.Controller;

import com.esprit.backend.Entity.CvStage;
import com.esprit.backend.Services.CvService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("stage")
public class CvStageController {
  private CvService cvService;

  @PostMapping("/upload")
  public ResponseEntity<CvStage> upload(
    @RequestParam("file") MultipartFile file,
    @RequestParam("nom") String nom,
    @RequestParam("prenom") String prenom,
    @RequestParam("classe") String classe) {
    try {
      byte[] CvFile = file.getBytes();

      CvStage cvStage = new CvStage();
      cvStage.setNom(nom);
      cvStage.setPrenom(prenom);
      cvStage.setClasse(classe);
      cvStage.setCvFile(CvFile);
      // Save the CV
      CvStage savedCv = cvService.AddCV(cvStage);
      return ResponseEntity.ok(savedCv);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> downloadSubmission(@PathVariable Long id) {
    CvStage cvStage = cvService.getCvById(id);
    if (cvStage != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_PDF);
      headers.setContentDispositionFormData("filename", "cv.pdf");
      headers.setContentLength(cvStage.getCvFile().length);
      return new ResponseEntity<>(cvStage.getCvFile(), headers, HttpStatus.OK);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
