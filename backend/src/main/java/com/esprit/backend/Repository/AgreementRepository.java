package com.esprit.backend.Repository;


import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.ValidationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgreementRepository extends JpaRepository<InternshipAgreement,Long> {
    List<InternshipAgreement> findByValidationType(ValidationType validationType);
    List<InternshipAgreement> findAll();
}
