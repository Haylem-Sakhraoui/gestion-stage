package com.esprit.backend.Controller;


import com.esprit.backend.Entity.*;
import com.esprit.backend.Repository.UserRepository;
import com.esprit.backend.Services.AgreementService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Agreement")
@AllArgsConstructor

public class AgreementController {
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/agreements")

    public ResponseEntity<InternshipAgreement> addAgreement(@RequestBody InternshipAgreement internshipAgreement) throws MessagingException {
        InternshipAgreement addAgreement = agreementService.addAgreement(internshipAgreement);
        return new ResponseEntity<>(addAgreement, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<InternshipAgreement>> getAllAgreement() {
        List<InternshipAgreement> allAgreement = agreementService.getAllAgreements ();
        return ResponseEntity.ok(allAgreement);
    }

    @PutMapping("/{agreementId}/accept")
    public ResponseEntity<InternshipAgreement> acceptAgreement (@PathVariable Long agreementId) {
        InternshipAgreement  updatedAgreement  = agreementService.updateAgreementStatus(agreementId, "acceptée");
        return ResponseEntity.ok(updatedAgreement );
    }

    @PutMapping("/{agreementId}/reject")
    public ResponseEntity< InternshipAgreement > rejecAgreement (@PathVariable Long agreementId) {
        InternshipAgreement  updateAgreement= agreementService.updateAgreementStatus(agreementId, "refusée");
        return ResponseEntity.ok(updateAgreement);
    }
    @GetMapping("/downloadPdf")
    public ResponseEntity<byte[]> downloadPdfForAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        try {
            byte[] pdfBytes = agreementService.generatePdfForUser(user.getId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "internship_agreements.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }}