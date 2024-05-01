package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.RequestRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RequestService implements IRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public InternshipRequest addRequest(InternshipRequest internshipRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        internshipRequest.setUser(user);
        internshipRequest.setStatus("en attente");
        return requestRepository.save(internshipRequest);
    }


    public List<InternshipRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public InternshipRequest updateRequestStatus(Long requestId, String newStatus) {
        InternshipRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        request.setStatus(newStatus);
        return requestRepository.save(request);
    }


    public byte[] generatePdfForUser(Long userId) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        List<InternshipRequest> requests = requestRepository.findByUser(user);

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

                for (InternshipRequest request : requests) {
                    contentStream.showText("Request ID: " + request.getRequestId());
                    contentStream.newLine();
                    contentStream.showText("Student Name: " + request.getStudentFirstName() + " " + request.getStudentLastName());
                    contentStream.newLine();
                    contentStream.showText("Class Grade: " + request.getClassGrade());
                    contentStream.newLine();
                    contentStream.showText("Student Email: " + request.getStudentEmail());
                    contentStream.newLine();
                    contentStream.showText("Supervisor Name: " + request.getSupervisorName());
                    contentStream.newLine();
                    contentStream.showText("Supervisor Email: " + request.getSupervisorEmail());
                    contentStream.newLine();
                    contentStream.showText("Company Name: " + request.getCompanyName());
                    contentStream.newLine();
                    contentStream.showText("Status: " + request.getStatus());
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