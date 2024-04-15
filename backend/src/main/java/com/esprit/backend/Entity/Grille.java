package com.esprit.backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Grille implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long grilleId;
  @Enumerated(EnumType.STRING)
  private TacheType tacheType;
  @Enumerated(EnumType.STRING)
  private SatisfactionType satisfactionType;


  private Double noteFinale;

}