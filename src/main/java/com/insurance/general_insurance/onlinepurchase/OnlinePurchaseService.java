package com.insurance.general_insurance.onlinepurchase;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insurance.general_insurance.Premium.PaymentMethod;
import com.insurance.general_insurance.ProductCatalogue.Policy;
import com.insurance.general_insurance.ProductCatalogue.PolicyRepository;
import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.user.repository.UserRepository;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.repository.VehicleRepository;

@Service
public class OnlinePurchaseService {

    private final OnlinePurchaseRepository onlinePurchaseRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public OnlinePurchaseService(OnlinePurchaseRepository onlinePurchaseRepository, UserRepository userRepository,
            PolicyRepository policyRepository, VehicleRepository vehicleRepository) {
        this.onlinePurchaseRepository = onlinePurchaseRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public OnlinePurchase purchasePolicy(String userEmail, OnlinePurchaseRequest request) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found with email: " + userEmail));

        Policy policy = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new Exception("Policy not found with ID: " + request.getPolicyId()));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new Exception("Vehicle not found with ID: " + request.getVehicleId()));

        // Simulate a payment process based on the payment method
        String paymentStatus = simulatePayment(request.getPaymentMethod());

        if ("SUCCESSFUL".equals(paymentStatus)) {
            // Create a new purchase record
            OnlinePurchase purchase = new OnlinePurchase();
            purchase.setUser(user);
            purchase.setPolicy(policy);
            purchase.setVehicle(vehicle);
            purchase.setPurchaseDate(LocalDate.now());
            purchase.setPaymentMethod(request.getPaymentMethod());
            purchase.setPaymentStatus(paymentStatus);
            purchase.setPolicyNumber(generatePolicyNumber());

            // Save the purchase record
            return onlinePurchaseRepository.save(purchase);
        } else {
            throw new Exception("Payment failed. Status: " + paymentStatus);
        }
    }

    private String generatePolicyNumber() {
        // Generates a unique policy number using UUID
        return "INS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String simulatePayment(PaymentMethod method) {
        if (method == PaymentMethod.CREDIT_CARD || method == PaymentMethod.DEBIT_CARD || method == PaymentMethod.UPI
                || method == PaymentMethod.NET_BANKING) {
            return "SUCCESSFUL";
        }
        return "PENDING_FOR_PAYMENT";
    }
}