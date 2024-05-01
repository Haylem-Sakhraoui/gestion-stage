package com.esprit.backend.Services;


import com.esprit.backend.Entity.*;
import com.esprit.backend.Repository.AssignmentRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor

public class AssignmentService implements IAssignmentService {
    AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    @Override
    public InternshipAssignmentLetter addAssignment(InternshipAssignmentLetter internshipAssignmentLetter) {

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        internshipAssignmentLetter.setUser(user);
        internshipAssignmentLetter.setStatus("en attente");
            return assignmentRepository.save(internshipAssignmentLetter);
        }
    public List<InternshipAssignmentLetter> getAllAssignment() {
        return  assignmentRepository.findAll();
    }

    public   InternshipAssignmentLetter updateAssignmentStatus(Long assignmentId, String newStatus) {
        InternshipAssignmentLetter  internshipAssignmentLetter = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        internshipAssignmentLetter.setStatus(newStatus);
        return assignmentRepository.save(internshipAssignmentLetter);
    }

    public byte[] generatePdfForUser(Long userId) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        List<InternshipAssignmentLetter> assignments = assignmentRepository.findByUser(user);

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

                for (InternshipAssignmentLetter assignment : assignments) {
                    contentStream.showText("Assignment ID: " + assignment.getAssignmentId());
                    contentStream.newLine();
                    contentStream.showText("Student Name: " + assignment.getFirstName() + " " + assignment.getLastName());
                    contentStream.newLine();
                    contentStream.showText("Class Grade: " + assignment.getClassGrade());
                    contentStream.newLine();
                    contentStream.showText("Student Email: " + assignment.getStudentEmail());
                    contentStream.newLine();
                    contentStream.showText("Company Name: " + assignment.getCompanyName());
                    contentStream.newLine();
                    contentStream.showText("Company Email: " + assignment.getCompanyEmail());
                    contentStream.newLine();
                    contentStream.showText("Stage Type: " + assignment.getStageType());
                    contentStream.newLine();
                    contentStream.showText("Start Date: " + assignment.getStartDate());
                    contentStream.newLine();
                    contentStream.showText("End Date: " + assignment.getEndDate());
                    contentStream.newLine();
                    contentStream.showText("Telephone Number: " + assignment.getTelephoneNumber());
                    contentStream.newLine();
                    contentStream.showText("Status: " + assignment.getStatus());
                    contentStream.newLine();
                    // Ajoutez d'autres attributs de InternshipAssignmentLetter ici selon vos besoins
                    contentStream.newLine();
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
}






