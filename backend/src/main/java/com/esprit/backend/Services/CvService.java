package com.esprit.backend.Services;

import com.esprit.backend.Entity.CvStage;
import com.esprit.backend.Repository.CvStageRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CvService implements ICVService{
  CvStageRepository CvRepo;
  private final CvParsingService cvParsingService;
  private final InformationExtractor informationExtractor;


  @Autowired
  public CvService(CvParsingService cvParsingService, InformationExtractor informationExtractor, CvStageRepository cvRepo) {
    this.cvParsingService = cvParsingService;
    this.informationExtractor = informationExtractor;
    this.CvRepo = cvRepo;
  }

  @Override
  public CvStage AddCV(CvStage cvStage) {
    try {
      String cvText = cvParsingService.parsePdf(cvStage.getCvFile());
      Set<String> skills = informationExtractor.extractSkills(cvText);
      cvStage.setSkills(skills);
      return CvRepo.save(cvStage);
    } catch (IOException e) {
      e.printStackTrace();
      // Consider a more robust error handling strategy here
    }
    return null; // Or handle this case appropriately
  }

  // Implement other methods...

  @Override
  public CvStage getCvById(long idCv) {
    return CvRepo.findById(idCv).get();
  }


  public List<CvStage> getAllCVs() {
    return (List<CvStage>) CvRepo.findAll();
  }

  @Override
  public CvStage AddCV(CvStage cvStage) {
    return CvRepo.save(cvStage) ;
  }

}
