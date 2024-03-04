package com.esprit.backend.Entity;

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
    @Enumerated(EnumType.STRING)
    private ValidationType validationType;
}




