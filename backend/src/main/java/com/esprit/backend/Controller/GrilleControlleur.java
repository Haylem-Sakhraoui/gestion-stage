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
    private GrilleService grilleService;
    @PostMapping("/add")
    public ResponseEntity<Grille> addGrille(@RequestBody Grille grille) {
        Grille newGrille = grilleService.addGrille(grille);
        return ResponseEntity.ok(newGrille);

    }
    @GetMapping("/getall")
    public List<Grille> getAllGrilles() {
        return grilleService.getAllGrilles();
}}