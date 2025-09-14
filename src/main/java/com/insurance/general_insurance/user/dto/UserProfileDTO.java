package com.insurance.general_insurance.user.dto;

import com.insurance.general_insurance.vehicle.dto.VehicleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String role;
    private List<VehicleDTO> vehicles;
    private List<PurchasedPolicyDTO> purchasedPolicies; // Added this line
}