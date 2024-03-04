package com.esprit.backend.Controller;

import com.esprit.backend.Entity.InternshipSubmission;
import com.esprit.backend.Services.PlagiarismDetectionService;
import com.esprit.backend.Services.SubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/submission")
@AllArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;
    private final PlagiarismDetectionService plagiarismDetectionService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadSubmission(@RequestParam("file") MultipartFile file, @RequestParam("report") String report) {
        try {
            byte[] pdfFile = file.getBytes();
            InternshipSubmission internshipSubmission = new InternshipSubmission();
            internshipSubmission.setPdfFile(pdfFile);
            internshipSubmission.setReport(report);
            InternshipSubmission savedSubmission = submissionService.addSubmission(internshipSubmission);
            return ResponseEntity.ok(savedSubmission);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadSubmission(@PathVariable Long id) {
        InternshipSubmission internshipSubmission = submissionService.getSubmissionById(id);
        if (internshipSubmission != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "submission.pdf");
            headers.setContentLength(internshipSubmission.getPdfFile().length);
            return new ResponseEntity<>(internshipSubmission.getPdfFile(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/checkPlagiarism")
    public ResponseEntity<?> checkPlagiarism(@RequestParam("file") MultipartFile file) {
        try {
            byte[] document = file.getBytes();
            String filename = file.getOriginalFilename();

            String submissionId = plagiarismDetectionService.submitDocumentForPlagiarismCheck(document, filename);
            // Utilisez submissionId pour récupérer les résultats de plagiat ultérieurement si nécessaire
            return ResponseEntity.ok("Document submitted for plagiarism check. Submission ID: " + submissionId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
