package com.esprit.backend.Services;

import com.esprit.backend.DTO.UserStatistics;
import com.esprit.backend.Repository.OffreRepository;
import com.esprit.backend.Repository.ReclamationRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class StatisticsService implements IStatisticsService  {
    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private OffreRepository offerRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserStatistics getUserStatistics() {
        int reclamationsCount = (int) reclamationRepository.count();
        int offersCount = (int) offerRepository.count();
        int activeUsersCount = userRepository.countActiveUsers(); // Implement this method in UserRepository

        return new UserStatistics(reclamationsCount, offersCount, activeUsersCount);
    }
}
