package com.esprit.backend.Repository;


import com.esprit.backend.Entity.InternshipSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<InternshipSubmission,Long> {
}
