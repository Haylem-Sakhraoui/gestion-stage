package com.esprit.backend.Services;

import com.esprit.backend.DTO.Response;
import com.esprit.backend.DTO.internshipSubmission;
import com.esprit.backend.Entity.InternshipSubmission;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.SubmissionRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.sqrt;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public InternshipSubmission addSubmission(InternshipSubmission internshipSubmission) {
        try {
            // Extract current user from token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();

            // Associate the submission with the current user
            internshipSubmission.setUser(currentUser);

            // Save the submission
            return submissionRepository.save(internshipSubmission);
        } catch (Exception e) {
            // Handle exception appropriately, you may want to log it
            throw new RuntimeException("Failed to add submission", e);
        }
    }



    public double calculatePlagiarismScore(String text1, String text2) {
        if (text1 == null || text2 == null) {
            log.error("One of the texts for plagiarism check is null.");
            return 0.0;
        }

        Map<String, Integer> wordFreq1 = toWordFrequencyMap(text1);
        Map<String, Integer> wordFreq2 = toWordFrequencyMap(text2);

        Set<String> uniqueWords = new HashSet<>(wordFreq1.keySet());
        uniqueWords.addAll(wordFreq2.keySet());

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (String word : uniqueWords) {
            int freq1 = wordFreq1.getOrDefault(word, 0);
            int freq2 = wordFreq2.getOrDefault(word, 0);
            dotProduct += freq1 * freq2;
            norm1 += freq1 * freq1;
            norm2 += freq2 * freq2;
        }

        double score = (norm1 == 0 || norm2 == 0) ? 0.0 : (dotProduct / (sqrt(norm1) * sqrt(norm2))) * 100; // Modification ici pour convertir en pourcentage

        log.info("Plagiarism score calculated: {}", score);
        return score;
    }
    private Map<String, Integer> toWordFrequencyMap(String text) {
        String[] words = text.toLowerCase().split("\\W+"); // Split on non-word characters and convert to lower case
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            if (word.isEmpty()) continue; // Ignore empty words that can be produced by split
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }
        return freqMap;
    }

    public String extractTextFromPdf(byte[] pdfData) throws IOException {
        if (pdfData == null || pdfData.length == 0) {
            log.error("PDF data is empty.");
            return "";
        }

        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfData))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            log.info("Text extracted from PDF.");
            return text;
        } catch (IOException e) {
            log.error("Failed to extract text from PDF.", e);
            throw e;
        }
    }
    public InternshipSubmission getSubmissionById(Long id) {
        return submissionRepository.findById(id).orElse(null);
    }

}
