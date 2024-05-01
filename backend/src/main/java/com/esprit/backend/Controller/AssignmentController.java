package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.InternshipAssignmentLetter;
import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.UserRepository;
import com.esprit.backend.Services.AssignmentService;
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
@RequestMapping("/Assignment")
@AllArgsConstructor
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/assignment")

    public ResponseEntity<InternshipAssignmentLetter> addAssignment (@RequestBody InternshipAssignmentLetter internshipAssignmentLetter) throws MessagingException {
        InternshipAssignmentLetter addAssignment = assignmentService.addAssignment(internshipAssignmentLetter);
        return new ResponseEntity<>(addAssignment, HttpStatus.CREATED);
    }
    @PutMapping("/{assignmentId}/accept")
    public ResponseEntity<InternshipAssignmentLetter> acceptAssignment(@PathVariable Long assignmentId) {
        InternshipAssignmentLetter  updatedAssignment  = assignmentService.updateAssignmentStatus(assignmentId, "acceptée");
        return ResponseEntity.ok(updatedAssignment );
    }

    @PutMapping("/{assignmentId}/reject")
    public ResponseEntity< InternshipAssignmentLetter> rejectAssignment (@PathVariable Long assignmentId) {
        InternshipAssignmentLetter  updatAssignment= assignmentService.updateAssignmentStatus(assignmentId, "refusée");
        return ResponseEntity.ok(updatAssignment);
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
            byte[] pdfBytes =assignmentService.generatePdfForUser(user.getId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "internship_assignments.pdf");

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
    }
}


