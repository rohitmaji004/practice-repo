    // Admin-only
 
package com.insurance.general_insurance.vehicle.service;

import java.util.List;

import com.insurance.general_insurance.vehicle.dto.VehicleRegistrationRequest;
import com.insurance.general_insurance.vehicle.entity.Vehicle;

public interface VehicleService {
    Vehicle registerVehicle(String userEmail, VehicleRegistrationRequest request) throws Exception;
    List<Vehicle> getVehiclesForUser(String userEmail) throws Exception;

    // Admin-only
    Vehicle updateVehicleOwnerName(Long vehicleId, String newOwnerName) throws Exception;
    void deleteVehicle(Long vehicleId) throws Exception;
       List<Vehicle> getAllVehicles();
    Vehicle updateVehicleByRegistrationNumber(String registrationNumber, String newOwnerName, Long newOwnerId) throws Exception;
    void deleteVehicleByRegistrationNumber(String registrationNumber) throws Exception;
}