package com.insurance.general_insurance.vehicle.dto;

import com.insurance.general_insurance.vehicle.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {
    private Long id;
    private String registrationNumber;
    private String vehicleName; // Changed from ownerName
    private VehicleType vehicleType; // Changed to Enum
}