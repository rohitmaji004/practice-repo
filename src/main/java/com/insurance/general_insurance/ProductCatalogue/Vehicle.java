package com.insurance.general_insurance.ProductCatalogue;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import com.insurance.general_insurance.user.entity.User;


@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String make;
    private String model;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Correct mappedBy
    private List<Policy> policies;
   

    // Getters and Setters
}

