package com.esprit.backend.DTO;

import com.esprit.backend.Entity.StatutReclamation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddReclamationRequest {

    private String description;

    private Long adminId;
}
