package com.esprit.backend.auth;

import com.esprit.backend.Configuration.JwtService;
import com.esprit.backend.Entity.Role;
import com.esprit.backend.Entity.User;
import com.esprit.backend.Repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.Entity;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public AuthenticationResponse register(RegisterRequest request) {

//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
//            try {
//                throw new AccessDeniedException("Only admins can register new users.");
//            } catch (AccessDeniedException e) {
//                throw new RuntimeException(e);
//            }
//        }

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

    public AuthResponse authenticate(AuthenticateRequest request) {
    /*authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var user =userRepository.findByEmail(request.getEmail())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(new HashMap<>(),user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();*/
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(request.getEmail());


            var user =userRepository.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(new HashMap<>(),user);
            return new AuthResponse(200,AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build(), "login successfully");

          /*  return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();*/
        }catch (BadCredentialsException e) {
            return new AuthResponse(404, "Invalid email or password");
        }
         catch (DisabledException d) {
            return new AuthResponse(404, "Account disabled ! check you email to enabled it.");

        }


    }


}
