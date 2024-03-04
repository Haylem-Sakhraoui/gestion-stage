package com.esprit.backend.Services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
    public class PlagiarismDetectionService {
        private final RestTemplate restTemplate;
        private final String apiKey = "a0d24bc3-888b-4064-9abb-8d37b1bcd68e"; // Remplacez par votre clé API
        private final String email = "zaafranimaram07@gmail.com"; // Remplacez par votre email utilisé pour l'inscription à Copyleaks

        @Autowired
        public PlagiarismDetectionService(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public String submitDocumentForPlagiarismCheck(byte[] document, String filename) throws URISyntaxException {
            final String submitUrl ="https://api.copyleaks.com/documentation/v3/scans/submit/file#1-request"; // URL de soumission, ajustez en fonction de votre plan Copyleaks

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(buildBody(document, filename), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(new URI(submitUrl), requestEntity, String.class);

            // Ici, vous pouvez extraire l'ID de soumission du document à partir de la réponse pour le suivi
            String submissionId = response.getHeaders().getLocation().toString();
            return submissionId;
        }

        private MultiValueMap<String, Object> buildBody(byte[] document, String filename) {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(document) {
                @Override
                public String getFilename() {
                    return filename;
                }
            });
            body.add("filename", filename);
            return body;
        }

        public String checkPlagiarismResults(String submissionId) {
            // Implémentez la logique pour récupérer les résultats en utilisant l'ID de soumission
            return submissionId;
        }
    }

