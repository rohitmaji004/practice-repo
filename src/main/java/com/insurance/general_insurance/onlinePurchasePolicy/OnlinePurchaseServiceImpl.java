package com.insurance.general_insurance.onlinePurchasePolicy;

import com.insurance.general_insurance.Premium.PaymentMethod;
import com.insurance.general_insurance.ProductCatalogue.Policy;
import com.insurance.general_insurance.ProductCatalogue.PolicyRepository;
import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.user.repository.UserRepository;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class OnlinePurchaseServiceImpl implements OnlinePurchaseService {

    private final OnlinePurchaseRepository onlinePurchaseRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final VehicleRepository vehicleRepository;

    public OnlinePurchaseServiceImpl(OnlinePurchaseRepository onlinePurchaseRepository,
                                     UserRepository userRepository,
                                     PolicyRepository policyRepository,
                                     VehicleRepository vehicleRepository) {
        this.onlinePurchaseRepository = onlinePurchaseRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public OnlinePurchase purchasePolicy(String userEmail, Long policyId, Long vehicleId, PaymentMethod paymentMethod) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new Exception("Policy not found"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new Exception("Vehicle not found"));

        OnlinePurchase newPurchase = new OnlinePurchase();
        newPurchase.setUser(user);
        newPurchase.setPolicy(policy);
        newPurchase.setVehicle(vehicle);
        newPurchase.setPaymentMethod(paymentMethod);
        newPurchase.setPurchaseDate(LocalDate.now());
        newPurchase.setPolicyNumber(UUID.randomUUID().toString().toUpperCase().substring(0, 13));
        newPurchase.setPaymentStatus("SUCCESS");


        return onlinePurchaseRepository.save(newPurchase);
    }
}