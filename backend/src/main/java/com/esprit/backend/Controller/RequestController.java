package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.UserRepository;
import com.esprit.backend.Services.RequestService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/request")
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")

    public ResponseEntity<InternshipRequest> addRequest(@RequestBody InternshipRequest internshipRequest) throws MessagingException {
        InternshipRequest addRequest = requestService.addRequest(internshipRequest);
        return new ResponseEntity<>(addRequest, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InternshipRequest>> getAllRequests() {
        List<InternshipRequest> allRequests = requestService.getAllRequests();
        return ResponseEntity.ok(allRequests);
    }

    @PutMapping("/{requestId}/accept")
    public ResponseEntity<InternshipRequest> acceptRequest(@PathVariable Long requestId) {
        InternshipRequest updatedRequest = requestService.updateRequestStatus(requestId, "acceptée");
        return ResponseEntity.ok(updatedRequest);
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<InternshipRequest> rejectRequest(@PathVariable Long requestId) {
        InternshipRequest updatedRequest = requestService.updateRequestStatus(requestId, "refusée");
        return ResponseEntity.ok(updatedRequest);
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
            byte[] pdfBytes = requestService.generatePdfForUser(user.getId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "internship_requests.pdf");

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