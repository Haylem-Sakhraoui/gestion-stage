package com.esprit.backend.Services;


import com.esprit.backend.DTO.AddReclamationRequest;
import com.esprit.backend.DTO.Response;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.StatutReclamation;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.ReclamationRepository;

import com.esprit.backend.Repository.UserRepository;
import com.esprit.backend.auth.Mail;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ReclamationService implements IReclamationService{
    private final  ReclamationRepository reclamationRepository;
    private final QrCodeGeneratorService qrCodeGeneratorService;
    private static final int SIZE = 5;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public Reclamation addReclamation(ReclamationWithUserDetails reclamationDetails) throws MessagingException {
        // Récupérer l'utilisateur par son id_user ou par son prénom, nom et email
        User user;
        if (reclamationDetails.getIduser() != null) {
            user = userRepository.findById(reclamationDetails.getIduser())
                    .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec l'id: " + reclamationDetails.getIduser()));
        } else {
            user = (User) userRepository.findByFirstnameAndLastnameAndEmail(
                    reclamationDetails.getFirstname(),
                    reclamationDetails.getLastname(),
                    reclamationDetails.getEmail()
            ).orElseThrow(() -> new NotFoundException(
                    "Utilisateur non trouvé avec le prénom: " + reclamationDetails.getFirstname() +
                            ", nom: " + reclamationDetails.getLastname() +
                            ", email: " + reclamationDetails.getEmail()
            ));
        }

        // Définir la date de création sur la date actuelle
        Date currentDate = new Date();

        // Créer l'objet Reclamation
        Reclamation reclamation = Reclamation.builder()
                .dateCreation(currentDate)
                .description(reclamationDetails.getDescription())
                .statutReclamation(reclamationDetails.getStatutReclamation())
                .user(user)
                .build();

        // Enregistrer la réclamation dans la base de données
        reclamation = reclamationRepository.save(reclamation);

        try {
            // Générer le code QR pour les détails de la réclamation
            byte[] qrCodeImage = generateQrCodeForReclamation(reclamation);

            // Envoyer la notification par e-mail avec le code QR
            sendReclamationNotification(reclamation, qrCodeImage);
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'échec de la génération du code QR ou de l'envoi de l'e-mail ici
            // Par exemple, vous pouvez enregistrer une entrée de journal pour suivre l'erreur
        }

        // Retourner la réclamation enregistrée
        return reclamation;
    }

    public void sendReclamationNotification(Reclamation reclamation, byte[] qrCodeImage) throws MessagingException {
        // Construire le message e-mail
        final String subject = "Nouvelle réclamation";
        String body =
                "<div style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">" +
                        "<div style=\"background-color: #ffffff; border-radius: 10px; padding: 20px;\">" +
                        "<h2 style=\"color: #333333;\">Bonjour " + reclamation.getUser().getFirstname() + " " + reclamation.getUser().getLastname() + "</h2>" +
                        "<hr style=\"border: 0; border-top: 1px solid #ddd;\">" +
                        "<h3 style=\"color: #333333;\">Code QR:</h3>" +
                        "<img src=\"data:image/png;base64," + Base64.getEncoder().encodeToString(qrCodeImage) + "\" alt=\"Code QR\" style=\"display: block; margin: 0 auto;\">" +
                        "</div>" +
                        "<p style=\"color: #666666; font-size: 12px; margin-top: 20px;\">Cet e-mail a été envoyé par le service de réclamations.</p>" +
                        "</div>";
        // Créer l'objet Mail
        Mail mail = new Mail(reclamation.getUser().getEmail(), subject, body);
        // Envoyer l'e-mail
        emailService.sendMail(mail);
    }

    private byte[] generateQrCodeForReclamation(Reclamation reclamation) throws MessagingException {
        try {
            // Generate QR code for reclamation details
            return qrCodeGeneratorService.generateQrCodeImage(reclamation, 200, 200);
        } catch (IOException e) {
            throw new MessagingException("Failed to generate QR code for reclamation details", e);
        }
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
        reclamationRepository.deleteById(idReclamation);
    }

    public void sendReclamationStatus(Reclamation reclamation, StatutReclamation newStatus) throws MessagingException, IOException {
        // Read the image file and encode it as a base64 string

        // Construct email message
        final String subject = "Reclamation Status Update";
        String body =
                "<html>" +
                        "<head>" +
                        "<style>" +
                        "  body { font-family: Arial, sans-serif; background-color: #f4f4f4; }" +
                        "  .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); position: relative; }" +
                        "  h3 { color: #333333; }" +
                        "  p { margin-bottom: 10px; color: #666666; }" +
                        "  strong { color: #333333; }" +
                        "  .footer { margin-top: 20px; text-align: center; color: #999999; font-size: 12px; }" +
                        "  .logo { position: absolute; bottom: 0; right: 0; width: 200px; height: auto; }" + // Réduire la largeur du logo
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='container'>" +
                        "<h3>Hello " + reclamation.getUser().getFirstname() + " " + reclamation.getUser().getLastname() + "</h3>" +
                        "<p>The status of your reclamation has been updated:</p>" +
                        "<p><strong>New Status:</strong> " + newStatus + "</p>" +
                        "<p>Thank you for being one of our family.</p>" +
                        "</div>" +
                        "<div class='footer'>" +
                        "<p>This email was sent by Services Stages.</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>";
        Mail mail = new Mail(reclamation.getUser().getEmail(), subject, body);
        emailService.sendMail(mail);
    }

    @Override
    public Response editClaimState(Long idReclamation, String claimState) {
        Reclamation claim = reclamationRepository.findById(idReclamation).orElse(null);
        if (claim != null) {
            StatutReclamation newStatus = null;
            switch (claimState) {
                case "EN_ATTENTE":
                    newStatus = StatutReclamation.EN_ATTENTE;
                    break;
                case "EN_COURS":
                    newStatus = StatutReclamation.EN_COURS;
                    break;
                case "RESOLUE":
                    newStatus = StatutReclamation.RESOLUE;
                    break;
                default:
                    return new Response(400, "Invalid claim state");
            }
            claim.setStatutReclamation(newStatus);
            reclamationRepository.save(claim); // Sauvegarde la mise à jour du statut

            try {
                // Envoi de l'e-mail de notification avec le nouveau statut
                sendReclamationStatus(claim, newStatus);
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
                // Gérer l'erreur d'envoi de l'e-mail ici
                return new Response(500, "Failed to send notification email");
            }

            return new Response(200, "Claim updated successfully");
        }
        return new Response(404, "Claim not found");
    }


    @Override
    public Response retrieveClaim(Long idReclamation) {
        Reclamation claim = reclamationRepository.findById(idReclamation).orElse(null);
        if (claim != null) {
            // Assuming that user details are stored in the 'user' field of the Reclamation entity
            String firstname = claim.getUser().getFirstname();
            String lastname = claim.getUser().getLastname();
            String email = claim.getUser().getEmail();

            // Check if dateCreation, description, and statut are not null before using them
            Date dateCreation = claim.getDateCreation();
            String description = claim.getDescription();
            StatutReclamation statutReclamation = claim.getStatutReclamation();

            // Create a new ReclamationWithUserDetails object
            ReclamationWithUserDetails userDetails = new ReclamationWithUserDetails(
                    firstname, lastname, email, dateCreation, description, statutReclamation);

            return new Response(200, "Claims retrieved Successfully", userDetails);
        }
        return new Response(404, "Claim not exist");
    }

    @Override
    public Page<Reclamation> getFilteredClaims(int sortCriteria, Pageable pageable) {
        return reclamationRepository.findAllWithSorting(sortCriteria, pageable);
    }

    @Override
    public Response addClaim(AddReclamationRequest request) {
        try {
            // Extract current user from token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();

            Reclamation reclamation = new Reclamation();
            reclamation.setDescription(request.getDescription());
            reclamation.setDateCreation(new Date());
            reclamation.setStatutReclamation(StatutReclamation.EN_ATTENTE);
            reclamation.setUser(currentUser); // Set the current user as the creator

            reclamationRepository.save(reclamation);

            // Generate QR code for the reclamation details
            byte[] qrCodeImage = generateQrCodeForReclamation(reclamation);

            // Send email notification with QR code
            sendReclamationNotification( reclamation, qrCodeImage);

            return new Response(200, "Reclamation added successfully");
        } catch (Exception e) {
            return new Response(400, "Something went wrong");
        }
    }



    @Override
    public List<ReclamationWithUserDetails> getRecByStatut(StatutReclamation statutReclamation) {
        List<Reclamation> reclamations = reclamationRepository.findByStatutReclamation(statutReclamation);
        return reclamations.stream()
                .map(reclamation -> new ReclamationWithUserDetails(
                        reclamation.getUser().getFirstname(),
                        reclamation.getUser().getLastname(),
                        reclamation.getUser().getEmail(),
                        reclamation.getDateCreation(),
                        reclamation.getDescription(),
                        reclamation.getStatutReclamation()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public List<ReclamationWithUserDetails> getReclamationsByUserFullName(String firstname, String lastname) {
        return reclamationRepository.findByFirstnameAndLastname(firstname, lastname);
    }

}