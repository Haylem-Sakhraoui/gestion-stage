package com.esprit.backend.Controller;

import com.esprit.backend.Services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/votes")
public class VoteController {

  @Autowired
  private VoteService voteService;

  @PostMapping("/like")
  public ResponseEntity<String> like(@RequestParam Long idStage) {
    // Call vote method with true for like
    voteService.vote(idStage, true);
    return ResponseEntity.ok("Voted like successfully");
  }

  @PostMapping("/dislike")
  public ResponseEntity<String> dislike(@RequestParam Long idStage) {
    // Call vote method with false for dislike
    voteService.vote(idStage, false);
    return ResponseEntity.ok("Voted dislike successfully");
  }
}

