package com.esprit.backend.Repository;

import com.esprit.backend.DTO.CountStatut;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.StatutReclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReclamationRepository extends CrudRepository<Reclamation,Long> {


    List<Reclamation> findByStatutReclamation(StatutReclamation statut);
    @Query("SELECT c FROM Reclamation c " +
            "ORDER BY " +
            "c.idReclamation DESC")
    Page<Reclamation> findAllWithPagination(Pageable pageable);



    @Query("SELECT c FROM Reclamation  c " +
            "WHERE (:sortCriteria = 1 AND c.statutReclamation = 'EN_ATTENTE') OR " +
            "(:sortCriteria = 2 AND c.statutReclamation = 'EN_COURS') OR " +
            "(:sortCriteria = 3 AND c.statutReclamation = 'RESOLUE')" +
            "ORDER BY " +
            "c.dateCreation DESC")
    Page<Reclamation> findAllWithSorting(int sortCriteria, Pageable pageable);
}
