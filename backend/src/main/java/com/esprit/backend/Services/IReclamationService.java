package com.esprit.backend.Services;

import com.esprit.backend.DTO.Response;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.StatutReclamation;

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
    Reclamation addReclamation(ReclamationWithUserDetails reclamationDetails);

    List<Reclamation> getReclamationsByStatut(StatutReclamation statut);

    void supprimerReclamationsResolues();

    List<Reclamation> getAllReclamations();

    List<ReclamationWithUserDetails> getAllReclamationsWithUsers();

    Reclamation modifierReclamation(long idReclamation, StatutReclamation newStatut, String newDescription);

    void deleteReclamationById(long idReclamation);

    Response editClaimState(Long claimId, String ClaimState);

    Response retrieveClaim(Long idReclamation);
}
