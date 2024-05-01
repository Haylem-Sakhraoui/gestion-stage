package com.esprit.backend.Repository;


import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<InternshipRequest,Long> {
    List<InternshipRequest> findByUser(User user);
}
