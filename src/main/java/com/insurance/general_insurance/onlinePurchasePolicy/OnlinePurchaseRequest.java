package com.insurance.general_insurance.onlinePurchasePolicy;

import com.insurance.general_insurance.Premium.PaymentMethod;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlinePurchaseRequest {
    private Long policyId;
    private Long vehicleId;
    private PaymentMethod paymentMethod;
}