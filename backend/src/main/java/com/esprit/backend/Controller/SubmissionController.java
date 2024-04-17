package com.esprit.backend.Controller;

import com.esprit.backend.Entity.InternshipSubmission;
import com.esprit.backend.Services.SubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/submission")
@AllArgsConstructor
public class SubmissionController {


    private final SubmissionService submissionService;
    private static final Logger log = LoggerFactory.getLogger(SubmissionController.class);
    @PostMapping("/upload")
    public ResponseEntity<?> uploadSubmission(@RequestParam("file") MultipartFile file) {
        try {
            byte[] pdfFile = file.getBytes();
            String extractedText = submissionService.extractTextFromPdf(pdfFile);
            log.info("Text extracted for plagiarism check: {}", extractedText);

            // Use a real document text here for comparison
            double plagiarismScore = submissionService.calculatePlagiarismScore(extractedText, "Code Quality: " +
                    "of the HTML, CSS, and JavaScrip" +"La relation de travail prend fin à son terme échu sans un délai de préavis." +
                    "A l’issue de cette période, l’employé recevra son certificat de travail et son solde de tout compte." +
                    "Article 5 : Ouverture d’un compte postal ou bancaire" +
                    "Le contractant, dans le cadre du présent contrat, s’engage à communiquer à la Société le numéro de compte postal ou bancaire ." +
                    "Article 6: Rupture de la relation de travail avant terme" +
                    "La relation de travail est rompue, du fait du travail, dans le cas de démission, de départ en retraite ou de décès." +
                    "La relation de travail est rompue du fait de l’employeur, dans les cas suivants :Dissolution du service, diminution subite ou fin " +
                    "anticipée des activités pour lesquelles l’employé a été recruté, transfert de la structure de rattachement, cessation d’activité" +
                    "légale de l’organisme employeur ,faute grave, en cas d’absence non justifiée dans les 24 h et sans autorisation préalable de" +
                    "l’employeur ou en cas d’abandon de poste ."+"Parcours « SYSTEMES D’INFORMATION DE L’ENTREPRISE ETENDUE : AUDIT ET CONSEIL »\n" +
                    "Formation en Apprentissage et Formation Continue" +
                    "Les candidats rédigeront un mémoire en anglais de 4 pages sur le sujet suivant :" +
                    "Organizations are putting more workloads into public cloud, including business-critical applications. The " +
                    "worldwide public cloud services market is projected to grow at a compound annual rate of nearly 22% and " +
                    "41% of enterprise workloads are expected to be running on public cloud platforms. But what about the idea " +
                    "of an organization outsourcing all of its applications—together with the confidential data stored in them—to " +
                    "one or more cloud providers? The mere suggestion of such a possibility may cause IT security and data " +
                    "protection experts to hyperventilate. Even now, 90% of cybersecurity professionals say they are concerned " +
                    "about cloud security, particularly data loss and leakage, threats to data privacy and breaches of " +
                    "confidentiality.");

            InternshipSubmission internshipSubmission = new InternshipSubmission();
            internshipSubmission.setPdfFile(pdfFile);

            // Suppose your report column can only handle 255 characters
            String truncatedText = extractedText.substring(0, Math.min(extractedText.length(), 255));

            internshipSubmission.setReport(truncatedText); // Set the truncated text as the report
            internshipSubmission.setPlagiarismScore(plagiarismScore);

            InternshipSubmission savedSubmission = submissionService.addSubmission(internshipSubmission);
            log.info("Submission saved with plagiarism score: {}", plagiarismScore);
            return ResponseEntity.ok(savedSubmission);
        } catch (IOException e) {
            log.error("Failed to upload submission.", e);
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
    }}

