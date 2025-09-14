package com.insurance.general_insurance.Premium;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PremiumDTO {
    private Long premiumId;
    private String policyName;
    private String vehicleRegistrationNumber;
    private Double amount;
    private LocalDate dueDate;
    private String status;
}
