package com.example.userstore.repository;

import com.example.userstore.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Address entity operations.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
} 