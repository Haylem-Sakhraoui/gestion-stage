package com.esprit.backend.Repository;


import com.esprit.backend.Entity.InternshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<InternshipRequest,Long> {
}
