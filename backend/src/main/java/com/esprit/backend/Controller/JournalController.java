package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.InternshipJournal;
import com.esprit.backend.Services.JournalService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Journal")
@AllArgsConstructor
public class JournalController {
    @Autowired
    private JournalService journalService;

    @PostMapping("/Journal")

    public ResponseEntity<InternshipJournal> addJournal(@RequestBody InternshipJournal internshipJournal) throws MessagingException {
        InternshipJournal addJournal = journalService.addJournal(internshipJournal);
        return new ResponseEntity<>(addJournal, HttpStatus.CREATED);
}}