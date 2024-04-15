package com.esprit.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReclamation;
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    private String description;
    @Enumerated(EnumType.STRING)
    private StatutReclamation statutReclamation;

    @ManyToOne(fetch = FetchType.EAGER)  // Ensure FetchType.EAGER here
    @JoinColumn(name = "user_id")  // Adjust the join column name based on your entity
    @JsonIgnore
    private User user;
}
