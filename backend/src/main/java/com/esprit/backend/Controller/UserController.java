package com.esprit.backend.Controller;

import com.esprit.backend.Entity.User;
import com.esprit.backend.Services.UserService;
import com.esprit.backend.auth.AuthenticationResponse;
import com.esprit.backend.auth.ForgetPassword;
import com.esprit.backend.auth.RegisterRequest;
import com.esprit.backend.auth.ResetPasswordRequest;
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

    @PostMapping("/adminaddUser")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthenticationResponse adminAddUser(@RequestBody RegisterRequest request){
        return userService.AdminAddUser(request);
    }
    @PostMapping("/serviceaddUser")
    @PreAuthorize("hasRole('SERVICESTAGE')")
    public AuthenticationResponse serviceAddUser(@RequestBody RegisterRequest request){
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

    @PutMapping("/disableUser/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public void disableUser(@PathVariable("email") String email){
        userService.disableUser(email);
    }

    @PutMapping("/enableUser/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public void enableUser(@PathVariable("email") String email) {
        userService.enableUser(email);
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

