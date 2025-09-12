package com.insurance.general_insurance.vehicle.controller;

import com.insurance.general_insurance.vehicle.dto.UpdateVehicleAdminRequest;
import com.insurance.general_insurance.vehicle.service.VehicleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VehicleDashboardController {
    private final VehicleService vehicleService;

    public VehicleDashboardController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // Admin-only: Update vehicle by registration number (form POST) for dashboard
    @PostMapping("/vehicles/by-registration/update")
    public String updateVehicleByRegistrationForm(@AuthenticationPrincipal UserDetails userDetails,
                                                  @ModelAttribute UpdateVehicleAdminRequest updateRequest) {
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/dashboard?error=Only admin can update vehicles";
        }
        try {
            vehicleService.updateVehicleByRegistrationNumber(
                updateRequest.getRegistrationNumber(),
                updateRequest.getOwnerName(),
                updateRequest.getOwnerId()
            );
            return "redirect:/dashboard?success=vehicle_updated";
        } catch (Exception e) {
            return "redirect:/dashboard?error=" + e.getMessage();
        }
    }

    // Admin-only: Delete vehicle by registration number (form POST) for dashboard
    @PostMapping("/vehicles/by-registration/delete")
    public String deleteVehicleByRegistrationForm(@AuthenticationPrincipal UserDetails userDetails,
                                                  @ModelAttribute UpdateVehicleAdminRequest updateRequest) {
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/dashboard?error=Only admin can delete vehicles";
        }
        try {
            vehicleService.deleteVehicleByRegistrationNumber(updateRequest.getRegistrationNumber());
            return "redirect:/dashboard?success=vehicle_deleted";
        } catch (Exception e) {
            return "redirect:/dashboard?error=" + e.getMessage();
        }
    }
}
