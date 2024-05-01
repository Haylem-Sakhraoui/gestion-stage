package com.esprit.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InternshipJournal implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long JournalId;

    private String internshipSubject;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String tasksToBePerformed;
    private String status = ""; // Peut être "en attente", "acceptée", ou "refusée"
    @ManyToOne(fetch = FetchType.EAGER)  // Ensure FetchType.EAGER here
    @JoinColumn(name = "user_id")  // Adjust the join column name based on your entity
    @JsonIgnore
    private User user;

}
