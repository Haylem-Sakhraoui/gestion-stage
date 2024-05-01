package com.esprit.backend.Controller;


import com.esprit.backend.Entity.Grille;
import com.esprit.backend.Entity.InternshipJournal;
import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.UserRepository;
import com.esprit.backend.Services.GrilleService;
import com.esprit.backend.Services.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Grille")
@AllArgsConstructor
public class GrilleControlleur {
    private final GrilleService grilleService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<Grille> addGrille(@RequestBody Grille grille) throws MessagingException {
        Grille addGrille = grilleService.addGrille(grille);
        return new ResponseEntity<>(addGrille, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Grille> getAllRequests() {
        List<Grille> allGrilles = grilleService.getAllGrilles ();
        return allGrilles;
    }



    @GetMapping("/grille_user")
   // @PreAuthorize("hasRole('SERVICESTAGE') or hasRole('ADMIN')")
    public List<Grille> getAllGrillesWithUsers() {
        return grilleService.getAllgrilleWithUsers();
    }

    @PutMapping("/{grilleId}/reject")
    public ResponseEntity< Grille > rejectGrille (@PathVariable Long grilleId) {
        Grille  updateGrille= grilleService.updateGrilleStatus(grilleId, "refusée");
        return ResponseEntity.ok(updateGrille);
    }
    @GetMapping("/calculateNote/{grilleId}")
    public ResponseEntity<Double> calculateNoteForGrille(@PathVariable Long grilleId) {
        Grille grille = grilleService.getGrilleById(grilleId);
        if (grille == null) {
            return ResponseEntity.notFound().build();
        }
        double note = grille.getNoteFinale(); // Utiliser directement la note finale de la grille
        return ResponseEntity.ok(note);
    }

    // L'endpoint pour calculer la note finale n'est plus nécessaire car la note est calculée automatiquement lors de l'ajout de la grille

    @PostMapping("/calculateGlobalNote")
    public ResponseEntity<Double> calculateGlobalNote() {
        double globalNote = grilleService.calculateGlobalNote();
        return ResponseEntity.ok(globalNote);
    }
}
