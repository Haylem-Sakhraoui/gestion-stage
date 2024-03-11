package com.esprit.backend.Controller;

import com.esprit.backend.DTO.CountStatut;
import com.esprit.backend.DTO.Response;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.StatutReclamation;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.ReclamationRepository;
import com.esprit.backend.Services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/reclamation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationController {
    @Autowired
    ReclamationService serviceReclamation;

    private final ReclamationRepository reclamationRepository;

    @Autowired
    public ReclamationController(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;

    }
   /* @PostMapping("/ajouterReclamationAvecUtilisateur/{userId}")
    public ResponseEntity<Reclamation> ajouterReclamationAvecUtilisateur(
            @RequestBody Reclamation reclamation,
            @PathVariable("userId") Long userId) {
        Reclamation newReclamation = serviceReclamation.ajouterReclamationAvecUtilisateur(reclamation, userId);
        return ResponseEntity.ok(newReclamation);
    }*/
        @PutMapping("/modifierReclamation/{idReclamation}")
        public ResponseEntity<Reclamation> modifierReclamation (
        @PathVariable long idReclamation,
        @RequestParam StatutReclamation newStatut,
        @RequestParam String newDescription){
            Reclamation updatedReclamation = serviceReclamation.modifierReclamation(idReclamation, newStatut, newDescription);
            return ResponseEntity.ok(updatedReclamation);
        }


        @GetMapping("/getReclamationsByStatut/{statut}")
        public ResponseEntity<List<Reclamation>> getReclamationsByStatut (@PathVariable StatutReclamation statut){
            List<Reclamation> reclamations = serviceReclamation.getReclamationsByStatut(statut);
            return ResponseEntity.ok(reclamations);
        }
        @DeleteMapping("/supprimerReclamationsResolues")
        public ResponseEntity<Void> supprimerReclamationsResolues () {
            serviceReclamation.supprimerReclamationsResolues();
            return ResponseEntity.noContent().build();
        }

   /* @PostMapping("/add")
    public ResponseEntity<String> addReclamation(@RequestBody ReclamationWithUserDetails reclamationDetails) {
        try {
            Reclamation reclamation = Reclamation.builder()
                    .dateCreation(reclamationDetails.getDateCreation())
                    .description(reclamationDetails.getDescription())
                    .statutReclamation(reclamationDetails.getStatutReclamation())
                    .user(User.builder()
                            .firstname(reclamationDetails.getFirstname())
                            .lastname(reclamationDetails.getLastname())
                            .email(reclamationDetails.getEmail())
                            .build())
                    .build();

            serviceReclamation.addReclamationWithDetails(reclamationDetails);
            return new ResponseEntity<>("Reclamation added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add reclamation", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
        @PostMapping("/addReclamation")
        public ResponseEntity<Reclamation> addReclamation (@RequestBody ReclamationWithUserDetails reclamationDetails){
            Reclamation addedReclamation = serviceReclamation.addReclamation(reclamationDetails);
            return new ResponseEntity<>(addedReclamation, HttpStatus.CREATED);
        }

        @GetMapping("/with-users")
        public List<ReclamationWithUserDetails> getAllReclamationsWithUsers () {
            return serviceReclamation.getAllReclamationsWithUsers();
        }

        @DeleteMapping("/deleteRec/{idReclamation}")
        public ResponseEntity<Void> deleteReclamation ( @PathVariable long idReclamation){
            serviceReclamation.deleteReclamationById(idReclamation);
            return ResponseEntity.noContent().build();
        }
/*
    @GetMapping("/percentCountStatut")
    public List<CountStatut> getPercentageGroupByStatut() {
        return serviceReclamation.getPercentageGroupByStatut();
    }
*/

        @PutMapping("/editClaimState/{idReclamation}/{StatutReclamation}")

        public Response editClaimState (@PathVariable("idReclamation") Long
        idReclamation, @PathVariable("StatutReclamation") String newClaimState){
            return serviceReclamation.editClaimState(idReclamation, newClaimState);
        }

        @GetMapping("/retrieveClaim/{idReclamation}")
        public Response retrieveClaim (@PathVariable("idReclamation") Long idReclamation){
            return serviceReclamation.retrieveClaim(idReclamation);
        }


    @GetMapping("/filteredClaims/{sortCriteria}/{page}")
    public Page<Reclamation> getFilteredClaims(@PathVariable("sortCriteria") int sortCriteria, @PathVariable("page") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return serviceReclamation.getFilteredClaims(sortCriteria, pageable);
    }


}

