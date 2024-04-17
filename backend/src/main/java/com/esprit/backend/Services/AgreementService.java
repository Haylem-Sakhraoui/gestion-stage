package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipAgreement;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.AgreementRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        internshipAgreement.setUser(user);
        return agreementRepository.save( internshipAgreement);
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

