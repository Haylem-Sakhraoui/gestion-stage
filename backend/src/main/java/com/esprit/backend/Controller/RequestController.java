package com.esprit.backend.Controller;


import com.esprit.backend.Entity.InternshipRequest;
import com.esprit.backend.Services.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request")
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/add")
    public InternshipRequest addRequest(@RequestBody InternshipRequest internshipRequest) {
        return requestService.addRequest(internshipRequest);
    }
}