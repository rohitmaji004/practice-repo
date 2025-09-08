package com.insurance.general_insurance.vehicle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRegistrationRequest {
    private String registrationNumber;
    private String vehicleType;
    private String ownerName;
}