package com.esprit.backend.Services;

import com.esprit.backend.Entity.User;
import com.esprit.backend.Entity.Vote;
import com.esprit.backend.Repository.OffreRepository;
import com.esprit.backend.Repository.UserRepository;
import com.esprit.backend.Repository.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoteService {

  VoteRepository voteRepository;
  UserRepository userRepository;
  OffreRepository offreRepository;

  public void vote(Long idStage, boolean like) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName(); // Assuming username uniquely identifies the user
    User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Optional<Vote> existingVote = voteRepository.findByUser_IdAndOffre_IdStage(currentUser.getId(), idStage);
    if (existingVote.isPresent()) {
      Vote vote = existingVote.get();
      vote.setLiked(like);
      voteRepository.save(vote);
    } else {
      Vote newVote = new Vote();
      newVote.setUser(currentUser);
      newVote.setOffre(offreRepository.findById(idStage).orElseThrow(() -> new EntityNotFoundException("Stage not found")));
      newVote.setLiked(like);
      voteRepository.save(newVote);
    }
  }
}
