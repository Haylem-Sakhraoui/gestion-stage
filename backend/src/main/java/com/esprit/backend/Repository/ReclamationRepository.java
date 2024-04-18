package com.esprit.backend.Repository;

import com.esprit.backend.DTO.ChartDataResponse;
import com.esprit.backend.DTO.CountStatut;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.ReclamationWithUserDetails;
import com.esprit.backend.Entity.StatutReclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT r FROM Reclamation r WHERE  r.user.firstname = :firstname AND r.user.lastname = :lastname ORDER BY r.idReclamation DESC")
    List<ReclamationWithUserDetails> findByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);

    @Query("SELECT NEW com.esprit.backend.DTO.ChartDataResponse(r.dateCreation, COUNT(r.idReclamation)) FROM Reclamation r GROUP BY r.dateCreation")
    List<ChartDataResponse> getReclamationChartData();

}
