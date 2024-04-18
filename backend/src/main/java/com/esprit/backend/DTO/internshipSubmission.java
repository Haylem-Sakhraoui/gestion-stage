package com.esprit.backend.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class internshipSubmission {

    private byte[] pdfFile;
    private String report;
}
