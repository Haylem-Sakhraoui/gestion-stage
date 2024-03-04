package com.esprit.backend.Services;

import com.esprit.backend.Entity.User;
import com.esprit.backend.auth.AuthenticationResponse;
import com.esprit.backend.auth.RegisterRequest;
import com.esprit.backend.auth.ResetPasswordRequest;
import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetails;
import springfox.documentation.service.Response;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    AuthenticationResponse AdminAddUser(RegisterRequest request);

    AuthenticationResponse ServiceStageAddUser(RegisterRequest request);

    List<User> retrieveAllUsers();

    List<User> retrieveAllServiceStage();

    List<User> retrieveAllSupervisor();

    List<User> retrieveAllStudent();

    void deleteUserByEmail(String email);

    Optional<User> getCurrentUser(String token);

    Optional<User> retrieveUserByEmail(String email);


    void disableUser(String email);


    void enableUser(String email);

    void resetPassword(ResetPasswordRequest request);

    void forgetPassword(String email) throws MessagingException;
}
