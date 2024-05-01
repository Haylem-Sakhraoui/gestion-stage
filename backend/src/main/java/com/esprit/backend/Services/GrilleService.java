package com.esprit.backend.Services;


import com.esprit.backend.Entity.Grille;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.GrilleRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GrilleService implements IGrilleService {
    private final GrilleRepository grilleRepository;
    private final UserRepository userRepository;

    public Grille addGrille(Grille grille) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        grille.setUser(user);

        // Ajouter la note finale lors de l'ajout de la grille
        double noteFinale = calculateNoteForGrille(grille);
        grille.setNoteFinale(noteFinale);
        grille.setStatus("en attente");
        return grilleRepository.save(grille);
    }

    public List< Grille > getAllGrilles() {
        return grilleRepository.findAll();
    }

    public  Grille  updateGrilleStatus(Long grilleId, String newStatus) {
        Grille  grille = grilleRepository.findById(grilleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        grille.setStatus(newStatus);
        return grilleRepository.save(grille);
    }

    public Grille getGrilleById(Long grilleId) {
        return grilleRepository.findById(grilleId).orElse(null);
    }

    public double calculateNoteForGrille(Grille grille) {
        double noteTotal = 0;
        int nombreTaches = 0;

        if (grille != null) {
            if (grille.getTacheType() != null && grille.getSatisfactionType() != null) {
                nombreTaches++;
                switch (grille.getSatisfactionType()) {
                    case EXCELENT:
                        noteTotal += 20;
                        break;
                    case Tres_Bon:
                        noteTotal += 17;
                        break;
                    case Satisfaisant:
                        noteTotal += 14;
                        break;
                    case Insatisfaisant:
                        noteTotal += 10;
                        break;
                }
            }
        }

        double noteMoyenne = (nombreTaches != 0) ? noteTotal / nombreTaches : 0;

        return noteMoyenne;
    }

    // Méthode pour calculer la note finale (cette méthode n'est plus nécessaire)
    // La note finale est désormais calculée et ajoutée automatiquement lors de l'ajout de la grille

    public double calculateGlobalNote() {
        List<Grille> grilles = grilleRepository.findAll();
        double totalNotes = 0;
        int totalTaches = 0;

        for (Grille grille : grilles) {
            double note = calculateNoteForGrille(grille);
            totalNotes += note;
            totalTaches++;
        }

        double globalNote = (totalTaches != 0) ? totalNotes / totalTaches : 0;
        return globalNote;
    }






    @Override
    public List<Grille> getAllgrilleWithUsers() {
        return grilleRepository.findAllGrilleWithUsers().stream()
                .map(grille -> {
                    Grille dto = new Grille();
                    dto.setGrilleId(grille.getGrilleId());
                    dto.setNoteFinale(grille.getNoteFinale());
                    dto.setTacheType(grille.getTacheType());
                    dto.setSatisfactionType(grille.getSatisfactionType());

                    // Assuming the user association is properly mapped in Grille entity
                    // You can set user details here if needed
                    dto.setUser(grille.getUser());

                    return dto;
                })
                .collect(Collectors.toList());
    }

}