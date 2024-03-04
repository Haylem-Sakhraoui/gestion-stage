package com.esprit.backend.Services;


import com.esprit.backend.Entity.Grille;
import com.esprit.backend.Repository.GrilleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GrilleService implements IGrilleService {
    GrilleRepository grilleRepository;

    @Override
    public Grille addGrille(Grille grille) {
        return grilleRepository.save(grille);
    }

    @Override
    public List<Grille> getAllGrilles() {
        return grilleRepository.findAll();
    }

}
