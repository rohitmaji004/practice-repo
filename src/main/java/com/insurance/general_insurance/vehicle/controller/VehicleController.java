package com.insurance.general_insurance.vehicle.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.general_insurance.vehicle.dto.UpdateVehicleAdminRequest;
import com.insurance.general_insurance.vehicle.dto.VehicleRegistrationRequest;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // ---------------- JSON-based endpoint ----------------
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> registerVehicle(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody VehicleRegistrationRequest request) {
        if (userDetails == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        try {
            Vehicle newVehicle = vehicleService.registerVehicle(userDetails.getUsername(), request);
            return new ResponseEntity<>(newVehicle, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ---------------- Form-based endpoint ----------------
    @PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded")
    public String registerVehicleForm(@AuthenticationPrincipal UserDetails userDetails,
                                      @ModelAttribute VehicleRegistrationRequest request) {
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

    // ---------------- Get vehicles for logged-in user ----------------
    @GetMapping
    public ResponseEntity<?> getMyVehicles(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        try {
            List<Vehicle> vehicles = vehicleService.getVehiclesForUser(userDetails.getUsername());
            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ---------------- Admin-only: Update vehicle owner ----------------
    @PutMapping("/{vehicleId}")
    public ResponseEntity<?> updateVehicleOwner(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable Long vehicleId,
                                                @RequestBody Vehicle updateRequest) {
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return new ResponseEntity<>("Only admin can update vehicles", HttpStatus.FORBIDDEN);
        }
        try {
            Vehicle updated = vehicleService.updateVehicleOwnerName(vehicleId, updateRequest.getOwnerName());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ---------------- Admin-only: Delete vehicle ----------------
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable Long vehicleId) {
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return new ResponseEntity<>("Only admin can delete vehicles", HttpStatus.FORBIDDEN);
        }
        try {
            vehicleService.deleteVehicle(vehicleId);
            return ResponseEntity.ok("Vehicle deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ---------------- Admin-only: View all vehicles ----------------
    @GetMapping("/all")
    public ResponseEntity<?> getAllVehicles(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return new ResponseEntity<>("Only admin can view all vehicles", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    // ---------------- Admin-only: Update vehicle by registration number ----------------
    @PutMapping("/by-registration/{registrationNumber}")
    public ResponseEntity<?> updateVehicleByRegistration(@AuthenticationPrincipal UserDetails userDetails,
                                                        @PathVariable String registrationNumber,
                                                        @RequestBody UpdateVehicleAdminRequest updateRequest) {
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return new ResponseEntity<>("Only admin can update vehicles", HttpStatus.FORBIDDEN);
        }
        try {
            Vehicle updated = vehicleService.updateVehicleByRegistrationNumber(
                registrationNumber,
                updateRequest.getOwnerName(),
                updateRequest.getOwnerId()
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ---------------- Admin-only: Delete vehicle by registration number ----------------
    @DeleteMapping("/by-registration/{registrationNumber}")
    public ResponseEntity<?> deleteVehicleByRegistration(@AuthenticationPrincipal UserDetails userDetails,
                                                        @PathVariable String registrationNumber) {
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return new ResponseEntity<>("Only admin can delete vehicles", HttpStatus.FORBIDDEN);
        }
        try {
            vehicleService.deleteVehicleByRegistrationNumber(registrationNumber);
            return ResponseEntity.ok("Vehicle deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Admin-only: Update vehicle by registration number (form POST) for web interface
    @PostMapping(value = "/by-registration/update", consumes = "application/x-www-form-urlencoded")
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

    // Admin-only: Delete vehicle by registration number (form POST) for web interface
    @PostMapping(value = "/by-registration/delete", consumes = "application/x-www-form-urlencoded")
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