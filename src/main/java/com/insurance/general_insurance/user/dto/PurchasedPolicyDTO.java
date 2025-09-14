package com.insurance.general_insurance.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PurchasedPolicyDTO {
    private String policyName;
    private String policyNumber;
    private LocalDate purchaseDate;
    private String vehicleName;
    private String registrationNumber;
}