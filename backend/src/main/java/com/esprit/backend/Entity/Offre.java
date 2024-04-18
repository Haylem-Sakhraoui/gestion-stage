package com.esprit.backend.Entity;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    @ElementCollection
    @JsonDeserialize(using = CompetenceDeserializer.class)
    private Set<String> Competence;

    @ManyToOne(fetch = FetchType.EAGER)  // Ensure FetchType.EAGER here
    @JoinColumn(name = "user_id")  // Adjust the join column name based on your entity
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy="offre",cascade={CascadeType.ALL})
    private Set<Vote> votes;
    public String getTypeStage() {
        return typeStage;
    }
    @JsonCreator
    public Offre(@JsonProperty("typeStage") String typeStage,
                 @JsonProperty("description") String description,
                 @JsonProperty("nbStagiaire") int nbStagiaire,
                 @JsonProperty("competence") Set<String> competence) {
        this.typeStage = typeStage;
        this.Description = description;
        this.NbStagiaire = nbStagiaire;
        this.Competence = competence != null ? new HashSet<>(competence) : new HashSet<>();
    }
    private int likes;
    private int dislikes;

}



