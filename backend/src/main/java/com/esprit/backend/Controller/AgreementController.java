package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.ValidationType;
import com.esprit.backend.Services.AgreementService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Agreement")
@AllArgsConstructor

public class AgreementController {
    @Autowired
    private AgreementService agreementService;

    @PostMapping("/agreements")
    public InternshipAgreement addAgreement(@RequestBody InternshipAgreement internshipAgreement) {
        return agreementService.addAgreement(internshipAgreement);
    }

    @GetMapping("/validationType")
    public List<String> getValidationTypesByUser(Long id) {
        List<InternshipAgreement> agreements = agreementService.getAgreementsByUser(id);
        List<String> validationTypes = agreements.stream()
                .filter(agreement -> agreement.getValidationType() != null) // Filtrer les accords avec un validationType non nul
                .map(agreement -> agreement.getValidationType().name())
                .collect(Collectors.toList());
        return validationTypes;
    }
}

