package com.esprit.backend.Services;

import com.esprit.backend.Configuration.JwtService;
import com.esprit.backend.Entity.Token;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.TokenRepository;
import com.esprit.backend.Repository.UserRepository;
import com.esprit.backend.auth.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;
    private static final int SIZE = 5;

    private final String clientUrl = "http://localhost:4200/resetPassword";

    @Override
    public AuthenticationResponse AdminAddUser(RegisterRequest request) {
        // Check if the user already exists
        if (userAlreadyExist(request.getEmail())) {
            throw new UnauthorizedUserException("User with email " + request.getEmail() + " already exists.");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .studentClass(request.getStudentClass())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(new HashMap<>(),user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();


    }
    @Override
    public AuthenticationResponse ServiceStageAddUser(RegisterRequest request) {

        if (userAlreadyExist(request.getEmail())) {
            throw new UnauthorizedUserException("User with email " + request.getEmail() + " already exists.");
        }
        // Validate the roles to be added (only "student" and "supervisor" roles allowed)
        if (!isValidRoles(String.valueOf(request.getRole()))) {
            throw new UnauthorizedUserException("Invalid roles. Only 'student' and 'supervisor' roles are allowed.");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .studentClass(request.getStudentClass())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(new HashMap<>(),user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
//    private boolean isUserAdmin(UserDetails userDetails) {
//        return userDetails.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
//    }
//    private boolean isUserServicestage(UserDetails userDetails) {
//        // determine if the user has the "servicestage" role
//        return userDetails.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("SERVICESTAGE"));
//    }
    private boolean isValidRoles(String role) {
        // Your logic to validate the roles to be added
        return role.equals("STUDENT") || role.equals("SUPERVISOR");
    }
    @Override
    public List<User> retrieveAllUsers() {

        return (List<User>) userRepository.findAll();
    }
    @Override
    public List<User> retrieveAllServiceStage() {
        List<User> users = retrieveAllUsers();
        List<User> serviceStage = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().toString().equals("SERVICESTAGE")) {
                serviceStage.add(user);
            }
        }
        return serviceStage;
    }

    @Override
    public List<User> retrieveAllSupervisor() {
        List<User> users = retrieveAllUsers();
        List<User> supervisor = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().toString().equals("SUPERVISOR")) {
                supervisor.add(user);
            }
        }
        return supervisor;
    }
    @Override
    public List<User> retrieveAllStudent() {
        List<User> users = retrieveAllUsers();
        List<User> student = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().toString().equals("STUDENT")) {
                student.add(user);
            }
        }
        return student;
    }
    @Override
    public void deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
        } else {
            throw new NotFoundException("User not found");   }
    }
    @Override
    public Optional<User> getCurrentUser(String token) {
        String username = jwtService.extractUsername(token);
        return userRepository.findByEmail(username);
    }
    @Override
    public Optional<User> retrieveUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void disableUser(abilityRequest request) {
        // Retrieve the user by email
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(false);
            userRepository.save(user);
        } else {
            throw new UnauthorizedUserException("User with email " + request.getEmail() + " not found.");
        }
    }

    @Override
    public void enableUser(abilityRequest request) {

        // Retrieve the user by email
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true); // Enable the user
            userRepository.save(user);
        } else {
            throw new UnauthorizedUserException("User with email " + request.getEmail() + " not found.");
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        // Retrieve user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Check if the current password matches
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Check if the new password matches the confirmed password
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        // Update user's password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Save the updated user
        userRepository.save(user);
    }
    @Override
    public void forgetPassword(String email) throws MessagingException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user =optionalUser.get();
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String token = jwtService.generateToken(new HashMap<>(),userDetails);

            // Create a new Token object and set the generated token
            Token userToken = user.getToken();
            if (userToken == null) {
                userToken = new Token();
                user.setToken(userToken);
            }
            userToken.setToken(token);

            // Save the Token object first
            tokenRepository.save(userToken);

            userRepository.save(user);
            final String subject = "Reset Password";
            String url = clientUrl ;
            String body =
                    "<div><h3>Hello " + user.getFirstname() + " " + user.getLastname() + " </h3>" +
                            "<br>" +
                            "<h4>A password reset request has been requested for your user account  " + user.getEmail() + " </h4>" +
                            "<h4>To confirm this request and set a new password, please <a href='" + url + "'>click here</a>.</h4>" +
                            "<h4>If you need help, please contact the website administrator.</h4>" +
                            "<br>" +
                            "<h4>Admin</h4></div>";
            Mail mail = new Mail(email, subject, body);
            emailService.sendMail(mail);
        } else {
            throw new NotFoundException("User not found");
        }
    }


    private String generateResetToken() {
        // Implement your logic to generate a unique password reset token
        // This can be a random string or a token generated based on user information
        // For example, you can use UUID.randomUUID().toString() to generate a random token
        return UUID.randomUUID().toString();
    }

    private String generateResetLink(String email, String resetToken) {
        // Implement your logic to generate the password reset link
        // This link should contain the user's email and the reset token
        // For example, you can construct the link like: "/reset-password?email=" + email + "&token=" + resetToken
        return "/reset-password?email=" + email + "&token=" + resetToken;
    }

    public class UnauthorizedUserException extends RuntimeException {
        public UnauthorizedUserException(String message) {
            super(message);
        }
    }

    private boolean userAlreadyExist(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

}
