package com.insurance.general_insurance.vehicle.repository;

import com.insurance.general_insurance.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUser_Id(Long userId);
    Vehicle findByRegistrationNumber(String registrationNumber);

    @Transactional
    @Modifying
    void deleteByRegistrationNumber(String registrationNumber);
}