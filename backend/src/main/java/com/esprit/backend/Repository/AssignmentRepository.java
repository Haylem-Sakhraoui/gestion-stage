package com.esprit.backend.Repository;


import com.esprit.backend.Entity.InternshipAssignmentLetter;
import com.esprit.backend.Entity.ValidationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<InternshipAssignmentLetter,Long> {

    List<InternshipAssignmentLetter> findAll();
}
