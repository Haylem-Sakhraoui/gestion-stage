package com.esprit.backend.Services;


import com.esprit.backend.Entity.Grille;

import java.util.List;

public interface IGrilleService {
    Grille addGrille(Grille grille);

    List<Grille> getAllgrilleWithUsers();
}
