package com.esprit.backend.Entity;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long idStage;

    String typeStage;

    String Description;
    int    NbStagiaire;

  private int likes;
  private int dislikes;
  @ElementCollection
  private Set<String> Competence;
  @OneToOne
  private User user;

  public String getTypeStage() {
    return typeStage;
  }
  @ManyToMany
  private Set<User> likedBy = new HashSet<>();

  @ManyToMany
  private Set<User> dislikedBy = new HashSet<>();
}
