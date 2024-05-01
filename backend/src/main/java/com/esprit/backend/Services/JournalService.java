package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipJournal;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.JournalRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class JournalService implements IJournalService {
    JournalRepository journalRepository;
    private final UserRepository userRepository;
    @Override
    public InternshipJournal addJournal(InternshipJournal internshipJournal) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
       internshipJournal.setUser(user);
        internshipJournal.setStatus("en attente");
        return journalRepository.save(internshipJournal);
    }
    public List<InternshipJournal> getJournalsForUser(User user) {
        return journalRepository.findByUser(user);
    }
    public InternshipJournal updateJournalStatus(Long journalId, String newStatus) {
        InternshipJournal internshipJournal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("ID de journal invalide"));
        internshipJournal.setStatus(newStatus);
        return journalRepository.save(internshipJournal);
    }
}
