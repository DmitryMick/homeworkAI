package com.example.userstore.service;

import com.example.userstore.dto.AuthenticationRequest;
import com.example.userstore.dto.AuthenticationResponse;
import com.example.userstore.dto.RegisterRequest;
import com.example.userstore.model.AuthUser;
import com.example.userstore.repository.AuthUserRepository;
import com.example.userstore.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;

/**
 * Service for authentication operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Register a new user.
     */
    public AuthenticationResponse register(@Valid RegisterRequest request) {
        log.info("Registering new user: {}", request.getEmail());

        if (authUserRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists");
        }

        AuthUser authUser = new AuthUser();
        authUser.setName(request.getName());
        authUser.setEmail(request.getEmail());
        authUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        AuthUser savedUser = authUserRepository.save(authUser);
        String jwtToken = jwtService.generateToken(savedUser.getEmail());

        log.info("User registered successfully: {}", savedUser.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticate user and generate JWT token.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Authenticating user: {}", request.getEmail());
        // Load user details
        UserDetails userDetails = loadUserByUsername(request.getEmail());
        // Generate JWT token
        String jwtToken = jwtService.generateToken(userDetails);
        log.info("User authenticated successfully: {}", request.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Load user details by username (email).
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                authUser.getEmail(),
                authUser.getPasswordHash(),
                new ArrayList<>()
        );
    }
} 