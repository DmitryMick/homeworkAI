package com.example.userstore.repository;

import com.example.userstore.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Company entity operations.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
} 