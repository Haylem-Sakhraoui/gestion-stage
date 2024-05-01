package com.esprit.backend.Repository;


import com.esprit.backend.Entity.Grille;
import com.esprit.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GrilleRepository extends JpaRepository<Grille,Long> {
    List<Grille> findByUser(User user);

    @Query("SELECT DISTINCT g FROM Grille g LEFT JOIN FETCH g.user")
    List<Grille> findAllGrilleWithUsers();
}
