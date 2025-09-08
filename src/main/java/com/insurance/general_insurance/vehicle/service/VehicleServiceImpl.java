package com.insurance.general_insurance.vehicle.service;

import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.user.repository.UserRepository;
import com.insurance.general_insurance.vehicle.dto.VehicleRegistrationRequest;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}