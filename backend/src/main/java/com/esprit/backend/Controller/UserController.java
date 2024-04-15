package com.esprit.backend.Controller;

import com.esprit.backend.DTO.UserStatistics;
import com.esprit.backend.DTO.abilityRequest;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Services.StatisticsService;
import com.esprit.backend.Services.UserService;
import com.esprit.backend.auth.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/stat")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserStatistics() {
        UserStatistics statistics = statisticsService.getUserStatistics();
        return ResponseEntity.ok(statistics);
    }

    @PostMapping("/adminaddUser")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthenticationResponse adminAddUser(@RequestBody RegisterRequest request) throws MessagingException {
        return userService.AdminAddUser(request);
    }
    @PostMapping("/serviceaddUser")
    @PreAuthorize("hasRole('SERVICESTAGE')")
    public AuthenticationResponse serviceAddUser(@RequestBody RegisterRequest request) throws MessagingException {
        return userService.ServiceStageAddUser(request);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> RetrieveUsers(){
        return userService.retrieveAllUsers();
    }

    @GetMapping("/servicestage")
    public List<User> retrieveAllServiceStage(){
        return userService.retrieveAllServiceStage();
    }

    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        Optional<User> currentUser = userService.getCurrentUser(jwtToken);
        return currentUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/supervisor")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICESTAGE')")
    public List<User> retrieveAllSupervisor(){
        return userService.retrieveAllSupervisor();
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICESTAGE')")
    public List<User> retrieveAllStudent(){
        return userService.retrieveAllStudent();
    }

    @DeleteMapping("/deleteUser/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable("email") String email)
    {
         userService.deleteUserByEmail(email);
    }

    @PutMapping("/disableUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void disableUser(@RequestBody abilityRequest request){

        userService.disableUser(String.valueOf(request));

        userService.disableUser(request);
    }

    @PutMapping("/enableUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void enableUser(@RequestBody abilityRequest request) {
        userService.enableUser(String.valueOf(request));
        userService.enableUser(request);
    }

    @PostMapping("/forgetPassword")
    public void forgetPassword(@RequestBody ForgetPassword request) throws Exception  {
        userService.forgetPassword(request.getEmail());
    }
    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody ResetPasswordRequest request)  {
        userService.resetPassword(request);
    }


}

