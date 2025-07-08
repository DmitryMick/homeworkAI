package com.example.userstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing geographical coordinates.
 */
@Entity
@Table(name = "geo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "lat", nullable = false)
    private String lat;
    
    @Column(name = "lng", nullable = false)
    private String lng;
} 