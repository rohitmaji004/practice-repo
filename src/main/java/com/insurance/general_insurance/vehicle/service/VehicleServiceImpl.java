package com.insurance.general_insurance.vehicle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.user.repository.UserRepository;
import com.insurance.general_insurance.vehicle.dto.VehicleRegistrationRequest;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Vehicle registerVehicle(String userEmail, VehicleRegistrationRequest request) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(request.getRegistrationNumber());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setOwnerName(request.getOwnerName());
        vehicle.setUser(user);

        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getVehiclesForUser(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));
        return vehicleRepository.findByUser_Id(user.getId());
    }

    // Admin-only: update owner name
    @Override
    public Vehicle updateVehicleOwnerName(Long vehicleId, String newOwnerName) throws Exception {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new Exception("Vehicle not found"));
        vehicle.setOwnerName(newOwnerName);
        return vehicleRepository.save(vehicle);
    }

    // Admin-only: delete vehicle
    @Override
    public void deleteVehicle(Long vehicleId) throws Exception {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new Exception("Vehicle not found");
        }
        vehicleRepository.deleteById(vehicleId);
    }
        // --- Admin-only methods ---
    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    @Transactional
    public Vehicle updateVehicleByRegistrationNumber(String registrationNumber, String newOwnerName, Long newOwnerId) throws Exception {
        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber);
        if (vehicle == null) {
            throw new Exception("Vehicle not found");
        }
        User newOwner = userRepository.findById(newOwnerId)
                .orElseThrow(() -> new Exception("New owner not found"));
        vehicle.setOwnerName(newOwnerName);
        vehicle.setUser(newOwner);
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteVehicleByRegistrationNumber(String registrationNumber) {
        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber);
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found");
        }
        vehicleRepository.delete(vehicle);
    }
}