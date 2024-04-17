package com.esprit.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatistics {
    private int reclamationsCount;
    private int offersCount;
    private int activeUsersCount;
}
