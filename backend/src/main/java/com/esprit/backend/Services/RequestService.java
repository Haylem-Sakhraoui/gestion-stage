package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Repository.RequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RequestService implements IRequestService {
    private final RequestRepository requestRepository;

    @Override
    public InternshipRequest addRequest(InternshipRequest internshipRequest) {
        return requestRepository.save(internshipRequest);
    }}