package com.esprit.backend.Controller;


import com.esprit.backend.Entity.*;
import com.esprit.backend.Repository.UserRepository;
import com.esprit.backend.Services.JournalService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Journal")
@AllArgsConstructor
public class JournalController {
    @Autowired
    private JournalService journalService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/Journal")

    public ResponseEntity<InternshipJournal> addJournal(@RequestBody InternshipJournal internshipJournal) throws MessagingException {
        InternshipJournal addJournal = journalService.addJournal(internshipJournal);
        return new ResponseEntity<>(addJournal, HttpStatus.CREATED);
}

    @GetMapping("/user")
    public ResponseEntity<List<InternshipJournal>> getJournalsForCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        List<InternshipJournal> userJournals = journalService.getJournalsForUser(user);

        return ResponseEntity.ok(userJournals);
    }
    @PutMapping("/{JournalId}/accept")
    public ResponseEntity<InternshipJournal> acceptJournal(@PathVariable Long JournalId) {
        InternshipJournal updatedJournal = journalService.updateJournalStatus(JournalId, "acceptée");
        return ResponseEntity.ok(updatedJournal);
    }

    @PutMapping("/{JournalId}/reject")
    public ResponseEntity<InternshipJournal> rejectJournal(@PathVariable Long JournalId) {
        InternshipJournal updatedJournal = journalService.updateJournalStatus(JournalId, "refusée");
        return ResponseEntity.ok(updatedJournal);
    }
    }
