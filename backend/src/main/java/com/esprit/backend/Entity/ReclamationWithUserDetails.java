package com.esprit.backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Date;
@Data
@Builder
        @AllArgsConstructor
@NoArgsConstructor
public class ReclamationWithUserDetails {
    private long idReclamation;
    private Date dateCreation;
    private String description;
    private StatutReclamation statutReclamation; // Keep the type as StatutReclamation
    private String firstname; // Add this property
    private String lastname; // Add this property
    private String email;
private Long iduser;

    // Constructors (if needed)

}

