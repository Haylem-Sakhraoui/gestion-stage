package com.esprit.backend.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ChartDataResponse {
    private Date dateCreation;
    private long reclamationCount;
    public ChartDataResponse(Date dateCreation, long reclamationCount) {
        this.dateCreation = dateCreation;
        this.reclamationCount = reclamationCount;
    }
}
