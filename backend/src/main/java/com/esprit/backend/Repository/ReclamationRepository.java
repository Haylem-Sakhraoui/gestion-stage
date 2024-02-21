package com.esprit.backend.Repository;

import com.esprit.backend.DTO.CountStatut;
import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.StatutReclamation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReclamationRepository extends CrudRepository<Reclamation,Long> {

    List<Reclamation> findByStatutReclamation(StatutReclamation statut);







}
