package com.esprit.backend.Controller;


import com.esprit.backend.Entity.Grille;
import com.esprit.backend.Services.GrilleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Grille")
@AllArgsConstructor
public class GrilleControlleur {
    @Autowired
    private final GrilleService grilleService;

    @PostMapping("/add")
    public ResponseEntity<Grille> addGrille(@RequestBody Grille grille) {
        Grille newGrille = grilleService.addGrille(grille);
        return ResponseEntity.ok(newGrille);
    }

    @GetMapping("/getall")
    public List<Grille> getAllGrilles() {
        return grilleService.getAllGrilles();
    }

    @GetMapping("/calculateNote/{grilleId}")
    public ResponseEntity<Double> calculateNoteForGrille(@PathVariable Long grilleId) {
        Grille grille = grilleService.getGrilleById(grilleId);
        if (grille == null) {
            return ResponseEntity.notFound().build();
        }
        double note = grilleService.calculateNoteForGrille(grille);
        return ResponseEntity.ok(note);
    }

    // Endpoint pour calculer la note finale
    @PostMapping("/calculateFinalNote/{grilleId}")
    public ResponseEntity<String> calculateFinalNote(@PathVariable Long grilleId) {
        grilleService.calculateFinalNote(grilleId);
        return ResponseEntity.ok("Note finale calculée avec succès pour la grille avec l'ID : " + grilleId);
    }
    @PostMapping("/calculateGlobalNote")
    public ResponseEntity<Double> calculateGlobalNote() {
        double globalNote = grilleService.calculateGlobalNote();
        return ResponseEntity.ok(globalNote);
    }

}

