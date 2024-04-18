package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.InternshipAssignmentLetter;
import com.esprit.backend.Entity.ValidationType;
import com.esprit.backend.Services.AgreementService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<InternshipAgreement> addAgreement(@RequestBody InternshipAgreement internshipAgreement) throws MessagingException {
        InternshipAgreement addAgreement = agreementService.addAgreement(internshipAgreement);
        return new ResponseEntity<>(addAgreement, HttpStatus.CREATED);
    }
}
