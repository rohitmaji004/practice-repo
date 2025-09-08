package com.insurance.general_insurance.vehicle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {
    private Long id;
    private String registrationNumber;
    private String vehicleType;
    private String ownerName;
}