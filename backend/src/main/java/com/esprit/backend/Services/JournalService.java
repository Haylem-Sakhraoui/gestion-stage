package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipJournal;
import com.esprit.backend.Repository.JournalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class JournalService implements IJournalService {
    JournalRepository journalRepository;

    @Override
    public InternshipJournal addJournal(InternshipJournal internshipJournal) {
        return journalRepository.save(internshipJournal);
    }
}
