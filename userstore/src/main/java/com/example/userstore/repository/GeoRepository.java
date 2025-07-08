package com.example.userstore.repository;

import com.example.userstore.model.Geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Geo entity operations.
 */
@Repository
public interface GeoRepository extends JpaRepository<Geo, Long> {
} 