package com.insurance.general_insurance.vehicle.dto;

import com.insurance.general_insurance.vehicle.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRegistrationRequest {
    private String registrationNumber;
    private String vehicleName; // Changed from ownerName
    private VehicleType vehicleType; // Changed to Enum
}