package com.insurance.general_insurance.onlinePurchasePolicy;

import com.insurance.general_insurance.Premium.PaymentMethod;

public interface OnlinePurchaseService {
    OnlinePurchase purchasePolicy(String userEmail, Long policyId, Long vehicleId, PaymentMethod paymentMethod) throws Exception;
}