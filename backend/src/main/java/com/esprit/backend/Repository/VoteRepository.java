package com.esprit.backend.Repository;

import com.esprit.backend.Entity.Reclamation;
import com.esprit.backend.Entity.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VoteRepository extends CrudRepository<Vote,Long> {


  Optional<Vote> findByUser_IdAndOffre_IdStage(Long userId, Long idStage);
}

