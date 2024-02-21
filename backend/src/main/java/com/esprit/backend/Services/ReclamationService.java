package com.esprit.backend.Services;


import com.esprit.backend.DTO.Response;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.StatutReclamation;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.ReclamationRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReclamationService implements IReclamationService{
    private final  ReclamationRepository reclamationRepository;
  private final UserRepository userRepository;
   /* @Override
    public Reclamation ajouterReclamation(Reclamation reclamation){
        reclamation = reclamationRepository.save(reclamation);
        return reclamation;
    }


    @Override
public List<Reclamation> getAllReclamation() {
        return (List<Reclamation>) reclamationRepository.findAll();
    }*/

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
    @Override
    public Reclamation addReclamation(ReclamationWithUserDetails reclamationDetails) {
        // Retrieve User by id_user or by firstname, lastname, and email
        User user;
        if (reclamationDetails.getIduser() != null) {
            user = userRepository.findById(reclamationDetails.getIduser())
                    .orElseThrow(() -> new NotFoundException("User not found with id: " + reclamationDetails.getIduser()));
        } else {

            user = (User) userRepository.findByFirstnameAndLastnameAndEmail(
                    reclamationDetails.getFirstname(),
                    reclamationDetails.getLastname(),
                    reclamationDetails.getEmail()
            ).orElseThrow(() -> new NotFoundException(
                    "User not found with firstname: " + reclamationDetails.getFirstname() +
                            ", lastname: " + reclamationDetails.getLastname() +
                            ", email: " + reclamationDetails.getEmail()
            ));
        }

        Reclamation reclamation = Reclamation.builder()
                .dateCreation(reclamationDetails.getDateCreation())
                .description(reclamationDetails.getDescription())
                .statutReclamation(reclamationDetails.getStatutReclamation())
                .user(user) // Associate the Reclamation with the User
                .build();

        // Save the Reclamation to the database
        return reclamationRepository.save(reclamation);
    }



    @Override
    public List<Reclamation> getReclamationsByStatut(StatutReclamation statut) {
        return reclamationRepository.findByStatutReclamation(statut);
    }

    @Override
    public void supprimerReclamationsResolues() {
        List<Reclamation> reclamationsResolues = reclamationRepository.findByStatutReclamation(StatutReclamation.RESOLUE);
        reclamationRepository.deleteAll(reclamationsResolues);
    }
    @Override
    public List<Reclamation> getAllReclamations() {
        return (List<Reclamation>) reclamationRepository.findAll();
    }
    @Override
    public List<ReclamationWithUserDetails> getAllReclamationsWithUsers() {
        Iterable<Reclamation> reclamationsIterable = reclamationRepository.findAll();

        // Convert Iterable to List
        List<Reclamation> reclamations = new ArrayList<>();
        reclamationsIterable.forEach(reclamations::add);

        return reclamations.stream()
                .map(reclamation -> {
                    ReclamationWithUserDetails dto = new ReclamationWithUserDetails();
                    dto.setIdReclamation(reclamation.getIdReclamation());
                    dto.setDateCreation(reclamation.getDateCreation());
                    dto.setDescription(reclamation.getDescription());

                    // Set StatutReclamation directly
                    dto.setStatutReclamation(reclamation.getStatutReclamation());

                    // Add null check for user
                    User user = reclamation.getUser();
                    if (user != null) {
                        dto.setFirstname(user.getFirstname());
                        dto.setLastname(user.getLastname());
                        dto.setEmail(user.getEmail());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
@Override
    public Reclamation modifierReclamation(long idReclamation, StatutReclamation newStatut, String newDescription) {
        Reclamation reclamation = reclamationRepository.findById(idReclamation)
                .orElseThrow(() -> new NotFoundException("Réclamation non trouvée pour cet id :: " + idReclamation));

        // Mise à jour du statut et de la description
        reclamation.setStatutReclamation(newStatut);
        reclamation.setDescription(newDescription);

        return reclamationRepository.save(reclamation);
    }
    @Override
    public void deleteReclamationById(long idReclamation) {
        reclamationRepository.findById(idReclamation);
    }
    @Override
    public Response editClaimState(Long idReclamation, String ClaimState) {
        Reclamation claim = reclamationRepository.findById(idReclamation).orElse(null);
        if(claim != null) {
            if (ClaimState.equals("EN_ATTENTE")) {
                claim.setStatutReclamation(StatutReclamation.EN_ATTENTE);
                reclamationRepository.save(claim);
                return new Response(200,"Claim updated successfully");
            }
            if (ClaimState.equals("EN_COURS")) {
                claim.setStatutReclamation(StatutReclamation.EN_COURS);
                reclamationRepository.save(claim);
                return new Response(200,"Claim updated successfully");
            }
            if (ClaimState.equals("RESOLUE")) {
                claim.setStatutReclamation(StatutReclamation.RESOLUE);
                reclamationRepository.save(claim);
                return new Response(200,"Claim updated successfully");
            }
        }
        return new Response(404,"Claim not exist");
    }
    @Override
    public Response retrieveClaim(Long idReclamation){
       Reclamation claim = reclamationRepository.findById(idReclamation).orElse(null);
        if(claim != null) {
            return new Response(200,"Claims retrieved Successfully",claim);
        }
        return new Response(404,"Claim not exist");
    }



}
