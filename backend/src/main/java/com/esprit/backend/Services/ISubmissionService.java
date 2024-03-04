package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipSubmission;

public interface ISubmissionService {
    InternshipSubmission addSubmission(InternshipSubmission internshipSubmission);
    InternshipSubmission getSubmissionById(Long id);


}
