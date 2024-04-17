package com.esprit.backend.Services;


import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.RequestRepository;
import com.esprit.backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RequestService implements IRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    @Override
    public InternshipRequest addRequest(InternshipRequest internshipRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
       internshipRequest.setUser(user);
        return requestRepository.save(internshipRequest);
    }

}
