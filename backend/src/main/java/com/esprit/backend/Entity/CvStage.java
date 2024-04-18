package com.esprit.backend.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CvStage implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  long idCv;
  long IdCv;
  private String Nom;
  private String Prenom;
  private String Classe;
  private byte[] CvFile;
  private String pdfUrl;

  public String getPdfUrl() {
    // Assuming your controller is mapped to "/stage"
    return "http://localhost:8075/stage/cv/cv/" + this.idCv + "/pdf";
  }

  @ElementCollection(fetch = FetchType.LAZY)
  private Set<String> skills = new HashSet<>();

  // Getters and Setters

  public Set<String> getSkills() {
    return skills;
  }

  public void setSkills(Set<String> skills) {
    this.skills = skills;
  }
  @OneToOne
  private User user;
}
