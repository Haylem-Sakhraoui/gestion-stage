package com.esprit.backend.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Vote {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idVote;  // Ensure this is the only field with auto-increment

  @ManyToOne(fetch = FetchType.EAGER)  // Ensure FetchType.EAGER here
  @JoinColumn(name = "user_id")  // Adjust the join column name based on your entity
  @JsonIgnore
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)  // Ensure FetchType.EAGER here
  @JoinColumn(name = "idStage")
  @JsonIgnore
  private Offre offre;

  private boolean liked;
}

