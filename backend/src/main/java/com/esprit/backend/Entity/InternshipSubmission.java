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
public class InternshipSubmission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] pdfFile;

    private String report;

    private double plagiarismScore;
    private String status = ""; // Peut être "en attente", "acceptée", ou "refusée"
    @OneToOne()
    private User user;

}
