package com.esprit.backend.Services;


import com.esprit.backend.DTO.internshipSubmission;
import com.esprit.backend.Entity.InternshipSubmission;

public interface ISubmissionService {
    //InternshipSubmission addSubmission(internshipSubmission internshipSubmission);
    InternshipSubmission getSubmissionById(Long id);


}
