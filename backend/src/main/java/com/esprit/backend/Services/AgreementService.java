package com.esprit.backend.Services;


import com.esprit.backend.Entity.Grille;
import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.AgreementRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AgreementService implements IAgreementService {
    @Autowired
    AgreementRepository agreementRepository;
    UserRepository  userRepository;


    @Override
    public InternshipAgreement addAgreement(InternshipAgreement internshipAgreement) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        internshipAgreement.setUser(user);
        internshipAgreement.setStatus("en attente");
        return agreementRepository.save( internshipAgreement);
    }

    public List< InternshipAgreement> getAllAgreements() {
        return  agreementRepository.findAll();
    }

    public   InternshipAgreement updateAgreementStatus(Long agreementId, String newStatus) {
        InternshipAgreement  internshipAgreement = agreementRepository.findById(agreementId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        internshipAgreement.setStatus(newStatus);
        return agreementRepository.save(internshipAgreement);
    }

    @Override
    public List<InternshipAgreement> getAgreementsByUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return agreementRepository.findByUserId(user.getId());



    }
    public byte[] generatePdfForUser(Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        List<InternshipAgreement> agreements = agreementRepository.findByUser(user);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.beginText();

                contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 50);

                contentStream.setNonStrokingColor(0, 0, 0);
                contentStream.setWordSpacing(2);
                contentStream.setLeading(20);

                for (InternshipAgreement agreement : agreements) {
                    contentStream.showText("Agreement ID: " + agreement.getAgreementId());
                    contentStream.newLine();
                    contentStream.showText("Student Name: " + agreement.getStudentFirstName() + " " + agreement.getStudentLastName());
                    contentStream.newLine();
                    contentStream.showText("Class Grade: " + agreement.getClassGrade());
                    contentStream.newLine();
                    contentStream.showText("Student Email: " + agreement.getStudentEmail());
                    contentStream.newLine();
                    contentStream.showText("Company Name: " + agreement.getCompanyName());
                    contentStream.newLine();
                    contentStream.showText("Supervisor Email: " + agreement.getSupervisorEmail());
                    contentStream.newLine();
                    contentStream.showText("Stage Type: " + agreement.getStageType());
                    contentStream.newLine();
                    contentStream.showText("Start Date: " + agreement.getStartDate());
                    contentStream.newLine();
                    contentStream.showText("End Date: " + agreement.getEndDate());
                    contentStream.newLine();
                    contentStream.showText("Status: " + agreement.getStatus());
                    contentStream.newLine();
                    // Ajoutez d'autres attributs de InternshipAgreement ici selon vos besoins
                    contentStream.newLine();
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }}