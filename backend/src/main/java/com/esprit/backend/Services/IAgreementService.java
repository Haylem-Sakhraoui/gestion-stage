package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.ValidationType;

import java.util.List;

public interface IAgreementService {
    InternshipAgreement addAgreement(InternshipAgreement internshipAgreement);

    List<InternshipAgreement> getAllAgreements();


    List<InternshipAgreement> getAgreementsByUser(Long id);
}
