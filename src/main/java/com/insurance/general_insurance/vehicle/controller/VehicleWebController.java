package com.insurance.general_insurance.vehicle.controller;

import com.insurance.general_insurance.vehicle.dto.VehicleRegistrationRequest;
import com.insurance.general_insurance.vehicle.service.VehicleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vehicle")
public class VehicleWebController {

    private final VehicleService vehicleService;

    public VehicleWebController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/register")
    public String registerVehicle(@AuthenticationPrincipal UserDetails userDetails,
                                  VehicleRegistrationRequest request,
                                  RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            vehicleService.registerVehicle(userDetails.getUsername(), request);
            redirectAttributes.addFlashAttribute("successMessage", "Vehicle added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding vehicle: " + e.getMessage());
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable Long vehicleId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            vehicleService.deleteVehicle(vehicleId);
            redirectAttributes.addFlashAttribute("successMessage", "Vehicle deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting vehicle: " + e.getMessage());
        }

        return "redirect:/dashboard";
    }
}