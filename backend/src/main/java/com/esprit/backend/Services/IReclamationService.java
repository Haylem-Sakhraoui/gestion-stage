package com.esprit.backend.Services;

import com.esprit.backend.DTO.AddReclamationRequest;
import com.esprit.backend.DTO.Response;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.StatutReclamation;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IReclamationService {
   // Reclamation ajouterReclamation(Reclamation reclamation);


    //List<Reclamation> getAllReclamation();

    /* @Override
         public Reclamation ajouterReclamation(Reclamation reclamation){
             reclamation = reclamationRepository.save(reclamation);
             return reclamation;
         }


         @Override
     public List<Reclamation> getAllReclamation() {
             return (List<Reclamation>) reclamationRepository.findAll();
         }*/
  //  Reclamation ajouterReclamationAvecUtilisateur(Reclamation reclamation, Long userId);

    //Reclamation addReclamation(ReclamationWithUserDetails reclamationDetails);

  //  void addReclamationWithDetails(ReclamationWithUserDetails reclamationDetails);

    //Response addReclamation(ReclamationWithUserDetails reclamationDetails);

    //Reclamation modifierStatutReclamation(long idReclamation, StatutReclamation newStatut);


    /* @Override
     public Reclamation ajouterReclamation(Reclamation reclamation){
         reclamation = reclamationRepository.save(reclamation);
         return reclamation;
     }


     @Override
 public List<Reclamation> getAllReclamation() {
         return (List<Reclamation>) reclamationRepository.findAll();
     }*/
 /*
     @Override
     public void addReclamationWithDetails(ReclamationWithUserDetails reclamationDetails) {
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

         // Save the reclamation using a repository or other method
         reclamationRepository.save(reclamation);
     }
     */
    Reclamation addReclamation(ReclamationWithUserDetails reclamationDetails) throws MessagingException;

    List<Reclamation> getReclamationsByStatut(StatutReclamation statut);

    void supprimerReclamationsResolues();

    List<Reclamation> getAllReclamations();

    List<ReclamationWithUserDetails> getAllReclamationsWithUsers();

    Reclamation modifierReclamation(long idReclamation, StatutReclamation newStatut, String newDescription);

    void deleteReclamationById(long idReclamation);

    Response editClaimState(Long claimId, String ClaimState);

    Response retrieveClaim(Long idReclamation);


    Page<Reclamation> getFilteredClaims(int sortCriteria, Pageable pageable);


    /*
        @Override
        public Response addClaimStudent(AddReclamationRequest request) {
            try {
                Reclamation claim = new Reclamation();
                claim.setDescription(request.getDescription());

                claim.setDateCreation(new Date());
                claim.setStatutReclamation(StatutReclamation.EN_ATTENTE);
                User admin = (User) userRepository.findById(request.getUser()).orElse(null);
                claim.setUser(admin);
                reclamationRepository.save(claim);
                return new Response(200,"Claim added successfully");
            }catch (Exception e){
                return new Response(400,"Something went wrong");
            }
        }


    */
    /*
        @Override
        public ResponseEntity<?> addClaim(@RequestBody Reclamation reclamation) {
            // Extract current user from token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();

            // Create a new Reclamation object
            Reclamation newReclamation = new Reclamation();

            // Set user and other details
            newReclamation.setUser(currentUser);
            newReclamation.setDescription(reclamation.getDescription());
            newReclamation.setStatutReclamation(StatutReclamation.EN_ATTENTE);
            newReclamation.setDateCreation(new Date());

            // Save the reclamation
            reclamationRepository.save(newReclamation);

            return ResponseEntity.ok("Reclamation added successfully.");
        }
    */
    Response addClaim(AddReclamationRequest request);

    List<ReclamationWithUserDetails> getRecByStatut(StatutReclamation statutReclamation);

    List<ReclamationWithUserDetails> getReclamationsByUserFullName(String firstname, String lastname);
}
