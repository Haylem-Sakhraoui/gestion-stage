package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipAssignmentLetter;
import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Services.AssignmentService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Assignment")
@AllArgsConstructor
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @PostMapping("/assignment")

    public ResponseEntity<InternshipAssignmentLetter> addAssignment (@RequestBody InternshipAssignmentLetter internshipAssignmentLetter) throws MessagingException {
        InternshipAssignmentLetter addAssignment = assignmentService.addAssignment(internshipAssignmentLetter);
        return new ResponseEntity<>(addAssignment, HttpStatus.CREATED);
    }

}
