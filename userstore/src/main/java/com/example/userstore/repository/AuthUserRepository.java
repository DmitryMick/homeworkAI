package com.example.userstore.repository;

import com.example.userstore.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for AuthUser entity operations.
 */
@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    
    /**
     * Find authentication user by email.
     */
    Optional<AuthUser> findByEmail(String email);
    
    /**
     * Check if authentication user exists by email.
     */
    boolean existsByEmail(String email);
} 