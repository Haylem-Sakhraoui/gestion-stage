package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipJournal;
import com.esprit.backend.Services.JournalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public InternshipJournal addJournal(@RequestBody InternshipJournal internshipJournal) {
        return journalService.addJournal(internshipJournal);
    }
}