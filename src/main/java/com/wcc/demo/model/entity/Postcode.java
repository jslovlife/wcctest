package com.wcc.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Column;

@Entity
@Data
@Table(name = "postcodelatlng")
public class Postcode {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8, unique = true)
    private String postcode;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;
}
