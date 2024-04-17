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
<<<<<<< HEAD
  AuthenticationResponse AdminAddUser(RegisterRequest request);

  AuthenticationResponse ServiceStageAddUser(RegisterRequest request);

  List<User> retrieveAllUsers();
=======
    AuthenticationResponse AdminAddUser(RegisterRequest request) throws MessagingException;

    AuthenticationResponse ServiceStageAddUser(RegisterRequest request) throws MessagingException;
>>>>>>> ae9697aeb5d34a336a0d9b34113ce0f9a8eb9262

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
