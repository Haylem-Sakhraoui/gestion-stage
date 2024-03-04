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
public class InternshipAgreement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agreementId;

    private String studentLastName;
    private String studentFirstName;
    private String classGrade;
    private String studentEmail;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private Long studentIdNumber;

    private String companyName;

    private String supervisorEmail;

    private String supervisorName;

    private String companyAddress;
    @Enumerated(EnumType.STRING)
    private StageType stageType;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Long internshipDuration;
    @Enumerated(EnumType.STRING)
    private ValidationType validationType;
}
