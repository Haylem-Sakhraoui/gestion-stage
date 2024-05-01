package com.esprit.backend.Repository;


import com.esprit.backend.Entity.InternshipJournal;
import com.esprit.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<InternshipJournal,Long> {
    List<InternshipJournal> findByUser(User user);
}
