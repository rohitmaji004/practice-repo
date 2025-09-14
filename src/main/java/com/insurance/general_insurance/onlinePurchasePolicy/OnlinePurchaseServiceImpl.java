package com.insurance.general_insurance.onlinePurchasePolicy;

import com.insurance.general_insurance.Premium.PaymentMethod;
import com.insurance.general_insurance.Premium.Premium;
import com.insurance.general_insurance.Premium.PremiumService;
import com.insurance.general_insurance.ProductCatalogue.Policy;
import com.insurance.general_insurance.ProductCatalogue.PolicyRepository;
import com.insurance.general_insurance.ProductCatalogue.RenewalFrequency;
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
    private final PremiumService premiumService;

    public OnlinePurchaseServiceImpl(OnlinePurchaseRepository onlinePurchaseRepository,
                                     UserRepository userRepository,
                                     PolicyRepository policyRepository,
                                     VehicleRepository vehicleRepository,
                                     PremiumService premiumService) {
        this.onlinePurchaseRepository = onlinePurchaseRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.vehicleRepository = vehicleRepository;
        this.premiumService = premiumService;
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

        OnlinePurchase savedPurchase = onlinePurchaseRepository.save(newPurchase);

        // Create the first premium record
        Premium firstPremium = new Premium();
        firstPremium.setPolicyId(policyId);
        firstPremium.setAmount(policy.getPremiumAmount());
        firstPremium.setDueDate(LocalDate.now()); // First payment is due now
        firstPremium.setStatus("PAID"); // Mark it as paid since this is the initial purchase
        premiumService.createPremium(firstPremium);

        // Create the next premium record if a renewal frequency is set
        if (policy.getRenewalFrequency() != null) {
            Premium nextPremium = new Premium();
            nextPremium.setPolicyId(policyId);
            nextPremium.setAmount(policy.getPremiumAmount());
            LocalDate nextDueDate = calculateNextDueDate(LocalDate.now(), policy.getRenewalFrequency());
            nextPremium.setDueDate(nextDueDate);
            nextPremium.setStatus("PENDING");
            premiumService.createPremium(nextPremium);
        }

        return savedPurchase;
    }

    private LocalDate calculateNextDueDate(LocalDate currentDate, RenewalFrequency frequency) {
        switch (frequency) {
            case MONTHLY: return currentDate.plusMonths(1);
            case QUARTERLY: return currentDate.plusMonths(3);
            case HALF_YEARLY: return currentDate.plusMonths(6);
            case YEARLY: return currentDate.plusYears(1);
            default: return currentDate.plusYears(1); // Default to yearly
        }
    }
}
