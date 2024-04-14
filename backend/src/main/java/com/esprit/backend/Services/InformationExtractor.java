package com.esprit.backend.Services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class InformationExtractor {


  public Set<String> extractSkills(String cvText) {
    Set<String> skills = new HashSet<>();
    String[] possibleSkills = {"Java", "Spring", "Angular", "SQL", "JavaScript"};

    for (String skill : possibleSkills) {
      if (cvText.contains(skill)) {
        skills.add(skill);
      }
    }

    return skills;
  }
}
