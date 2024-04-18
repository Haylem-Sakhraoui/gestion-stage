package com.esprit.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InternshipRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private String studentLastName;
    private String studentFirstName;
    private String classGrade;
    private String studentEmail;
    private String companyName;
    private String supervisorName;
    private String supervisorEmail;

    @ManyToOne(fetch = FetchType.EAGER)  // Ensure FetchType.EAGER here
    @JoinColumn(name = "user_id")  // Adjust the join column name based on your entity
    @JsonIgnore
    private User user;
}
