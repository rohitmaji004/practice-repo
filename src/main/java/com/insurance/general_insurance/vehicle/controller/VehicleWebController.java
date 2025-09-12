package com.insurance.general_insurance.vehicle.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.insurance.general_insurance.vehicle.dto.VehicleRegistrationRequest;
import com.insurance.general_insurance.vehicle.service.VehicleService;

@Controller
public class VehicleWebController {
    private final VehicleService vehicleService;

    public VehicleWebController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/vehicles/register")
    public String registerVehicleSession(@AuthenticationPrincipal UserDetails userDetails,
                                         VehicleRegistrationRequest request) {
        if (userDetails == null) {
            return "redirect:/login?error=not_authenticated";
        }
        try {
            vehicleService.registerVehicle(userDetails.getUsername(), request);
            return "redirect:/dashboard?success=vehicle_registered";
        } catch (Exception e) {
            return "redirect:/dashboard?error=" + e.getMessage();
        }
    }
}