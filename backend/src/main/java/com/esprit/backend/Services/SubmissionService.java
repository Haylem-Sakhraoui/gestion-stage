package com.esprit.backend.Services;

import com.esprit.backend.Entity.InternshipSubmission;
import com.esprit.backend.Repository.SubmissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubmissionService implements ISubmissionService {
    private final SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public InternshipSubmission addSubmission(InternshipSubmission internshipSubmission) {
        return submissionRepository.save(internshipSubmission);
    }

    @Override
    public InternshipSubmission getSubmissionById(Long id) {
        return submissionRepository.findById(id).orElse(null);
    }}