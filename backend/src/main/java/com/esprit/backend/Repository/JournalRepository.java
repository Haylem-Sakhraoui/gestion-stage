package com.esprit.backend.Repository;


import com.esprit.backend.Entity.InternshipJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<InternshipJournal,Long> {
}
