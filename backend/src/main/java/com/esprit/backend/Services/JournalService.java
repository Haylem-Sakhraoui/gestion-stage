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
        return journalRepository.save(internshipJournal);
    }
}
