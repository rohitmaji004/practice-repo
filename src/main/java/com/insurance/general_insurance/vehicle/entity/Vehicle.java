package com.insurance.general_insurance.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insurance.general_insurance.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;
    private String vehicleName; // Renamed from ownerName

    @Enumerated(EnumType.STRING) // To store the enum as a string
    private VehicleType vehicleType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}