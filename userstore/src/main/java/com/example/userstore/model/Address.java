package com.example.userstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing address information.
 */
@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "street", nullable = false)
    private String street;
    
    @Column(name = "suite", nullable = false)
    private String suite;
    
    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "zipcode", nullable = false)
    private String zipcode;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "geo_id", referencedColumnName = "id")
    private Geo geo;
} 