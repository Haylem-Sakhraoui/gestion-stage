package com.esprit.backend.Repository;

import com.esprit.backend.Entity.Offre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepository extends CrudRepository<Offre,Long> {
}
