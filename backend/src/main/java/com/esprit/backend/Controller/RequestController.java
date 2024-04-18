package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Services.RequestService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/add")

    public ResponseEntity< InternshipRequest> addRequest (@RequestBody InternshipRequest internshipRequest) throws MessagingException {
        InternshipRequest addRequest = requestService.addRequest(internshipRequest);
        return new ResponseEntity<>(addRequest, HttpStatus.CREATED);
}}