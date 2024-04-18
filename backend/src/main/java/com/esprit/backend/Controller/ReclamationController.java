package com.esprit.backend.Controller;

import com.esprit.backend.DTO.AddReclamationRequest;
import com.esprit.backend.DTO.CountStatut;
import com.esprit.backend.DTO.Response;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.StatutReclamation;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.ReclamationRepository;
import com.esprit.backend.Services.ReclamationService;
import io.swagger.annotations.Api;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/reclamation")
@Api(tags = "Gestion de reclamation")
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

      //  @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('STUDENT') or hasRole('ADMIN')")

        @PreAuthorize("hasRole('SERVICESTAGE')  or hasRole('ADMIN')")

        public ResponseEntity<Reclamation> addReclamation (@RequestBody ReclamationWithUserDetails reclamationDetails) throws MessagingException {
            Reclamation addedReclamation = serviceReclamation.addReclamation(reclamationDetails);
            return new ResponseEntity<>(addedReclamation, HttpStatus.CREATED);
        }
    @PostMapping("/addClaim")


    @PreAuthorize(" hasRole('STUDENT')")

    public ResponseEntity<?> addClaim(@RequestBody AddReclamationRequest request) {
        Response response = serviceReclamation.addClaim(request);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @GetMapping("/with-users")

       // @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")

        @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")

        public List<ReclamationWithUserDetails> getAllReclamationsWithUsers () {
            return serviceReclamation.getAllReclamationsWithUsers();
        }

        @DeleteMapping("/deleteRec/{idReclamation}")
       // @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")

        @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")

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

//@PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")
@PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")
        public Response editClaimState (@PathVariable("idReclamation") Long
        idReclamation, @PathVariable("StatutReclamation") String newClaimState){
            return serviceReclamation.editClaimState(idReclamation, newClaimState);
        }

        @GetMapping("/retrieveClaim/{idReclamation}")
      //  @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")

        @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")

        public Response retrieveClaim (@PathVariable("idReclamation") Long idReclamation){
            return serviceReclamation.retrieveClaim(idReclamation);
        }


    @GetMapping("/filteredClaims/{sortCriteria}/{page}")
   // @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")
    public Page<Reclamation> getFilteredClaims(@PathVariable("sortCriteria") int sortCriteria, @PathVariable("page") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return serviceReclamation.getFilteredClaims(sortCriteria, pageable);
    }
    @GetMapping("/byStatut/{statutReclamation}")
    public ResponseEntity<List<ReclamationWithUserDetails>> getRecByStatut(@PathVariable StatutReclamation statutReclamation) {
        List<ReclamationWithUserDetails> reclamations = serviceReclamation.getRecByStatut(statutReclamation);
        return ResponseEntity.ok().body(reclamations);
    }
    @GetMapping("/Rechreclamations")
    public List<ReclamationWithUserDetails> getReclamationsByUserFullName(@RequestParam String firstname, @RequestParam String lastname) {
        return serviceReclamation.getReclamationsByUserFullName(firstname, lastname);
    }
}

