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
public class InternshipAssignmentLetter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    private String lastName;
    private String firstName;
    private String classGrade;
    private String studentEmail;

    private String companyName;
    private String companyEmail;
    @Enumerated(EnumType.STRING)
    private StageType stageType;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private Long telephoneNumber;
    private String status = ""; // Peut être "en attente", "acceptée", ou "refusée"

    @ManyToOne(fetch = FetchType.EAGER)  // Ensure FetchType.EAGER here
    @JoinColumn(name = "user_id")  // Adjust the join column name based on your entity
    @JsonIgnore
    private User user;
}




