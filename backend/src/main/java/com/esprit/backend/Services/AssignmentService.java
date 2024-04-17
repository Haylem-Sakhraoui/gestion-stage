package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipAssignmentLetter;
import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Entity.ValidationType;
import com.esprit.backend.Repository.AssignmentRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor

public class AssignmentService implements IAssignmentService {
    AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    @Override
    public InternshipAssignmentLetter addAssignment(InternshipAssignmentLetter internshipAssignmentLetter) {

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        internshipAssignmentLetter.setUser(user);
            return assignmentRepository.save(internshipAssignmentLetter);
        }


        @Override
    public List<InternshipAssignmentLetter> getAllAssignment() {
        return assignmentRepository.findAll();
    }


    }

