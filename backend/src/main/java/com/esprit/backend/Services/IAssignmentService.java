package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipAssignmentLetter;
import com.esprit.backend.Entity.ValidationType;

import java.util.List;

public interface IAssignmentService {
    InternshipAssignmentLetter addAssignment(InternshipAssignmentLetter internshipAssignmentLetter);
    List<InternshipAssignmentLetter> getAllAssignment();


}
