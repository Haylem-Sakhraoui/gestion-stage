package com.esprit.backend.Services;


import com.esprit.backend.Entity.Grille;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.GrilleRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GrilleService {
    private final GrilleRepository grilleRepository;
    private final UserRepository userRepository;

    public Grille addGrille(Grille grille) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        grille.setUser(user);
        return grilleRepository.save(grille);
    }

    public List<Grille> getAllGrilles() {
        return grilleRepository.findAll();
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

    // Méthode pour calculer la note finale
    public void calculateFinalNote(Long grilleId) {
        Grille grille = grilleRepository.findById(grilleId).orElse(null);
        if (grille != null) {
            double noteFinale = calculateNoteForGrille(grille);
            grille.setNoteFinale(noteFinale);
            grilleRepository.save(grille);
        }
    }
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
}

