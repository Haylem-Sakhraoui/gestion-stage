package com.esprit.backend.DTO;

import com.esprit.backend.Entity.StatutReclamation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CountStatut {
    private Double percentage;
    private long count;
    private StatutReclamation statutReclamation;

    // Constructor that matches the query result
    public CountStatut(Double percentage, StatutReclamation statutReclamation) {
        this.percentage = percentage;
        this.statutReclamation = statutReclamation;


    }
}
