package com.esprit.backend.Services;

import com.esprit.backend.Repository.ReclamationRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@NoArgsConstructor
public class StatisticsService implements IStatisticsService  {
    @Autowired
    private ReclamationRepository reclamationRepository;
    @Override
    public List<Object> getReclamationStat(){
        List<Object> result = Collections.singletonList(reclamationRepository.getReclamationChartData());

        return result;
    }

}
