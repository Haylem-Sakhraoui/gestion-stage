package com.esprit.backend.Services;

import com.esprit.backend.DTO.abilityRequest;
import com.esprit.backend.Entity.User;
import com.esprit.backend.auth.AuthenticationResponse;
import com.esprit.backend.auth.RegisterRequest;
import com.esprit.backend.auth.ResetPasswordRequest;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    AuthenticationResponse AdminAddUser(RegisterRequest request) throws MessagingException;

    AuthenticationResponse ServiceStageAddUser(RegisterRequest request) throws MessagingException;

    List<User> retrieveAllUsers();

    List<User> retrieveAllServiceStage();

    List<User> retrieveAllSupervisor();

    List<User> retrieveAllStudent();

    void deleteUserByEmail(String email);

    Optional<User> getCurrentUser(String token);

    Optional<User> retrieveUserByEmail(String email);


    void disableUser(abilityRequest request);

    void enableUser(abilityRequest request);

    void resetPassword(ResetPasswordRequest request);

    void forgetPassword(String email) throws MessagingException;
}
