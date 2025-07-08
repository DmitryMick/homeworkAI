package com.example.userstore.repository;

import com.example.userstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by username.
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by email.
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if user exists by username.
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if user exists by email.
     */
    boolean existsByEmail(String email);
    
    /**
     * Find users by name containing the given string (case-insensitive).
     */
    List<User> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find users by city.
     */
    @Query("SELECT u FROM User u JOIN u.address a WHERE a.city = :city")
    List<User> findByCity(@Param("city") String city);
} 