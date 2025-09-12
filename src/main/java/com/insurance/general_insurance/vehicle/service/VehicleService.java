package com.insurance.general_insurance.vehicle.service;

import com.insurance.general_insurance.vehicle.dto.VehicleRegistrationRequest;
import com.insurance.general_insurance.vehicle.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle registerVehicle(String userEmail, VehicleRegistrationRequest request) throws Exception;
    List<Vehicle> getVehiclesForUser(String userEmail) throws Exception;
}