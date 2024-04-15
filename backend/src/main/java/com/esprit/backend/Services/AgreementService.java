package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.AgreementRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AgreementService implements IAgreementService {
    @Autowired
    AgreementRepository agreementRepository;
    UserRepository  userRepository;


    @Override
    public InternshipAgreement addAgreement(InternshipAgreement internshipAgreement) {
        return agreementRepository.save(internshipAgreement);
    }

    @Override
    public List<InternshipAgreement> getAllAgreements() {
        return agreementRepository.findAll();
    }


    @Override
    public List<InternshipAgreement> getAgreementsByUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return agreementRepository.findByUserId(user.getId());



    }}

