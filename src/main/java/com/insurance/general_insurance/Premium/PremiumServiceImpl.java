package com.insurance.general_insurance.Premium;

import com.insurance.general_insurance.ProductCatalogue.Policy;
import com.insurance.general_insurance.ProductCatalogue.PolicyRepository;
import com.insurance.general_insurance.ProductCatalogue.RenewalFrequency;
import com.insurance.general_insurance.onlinePurchasePolicy.OnlinePurchaseRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremiumServiceImpl implements PremiumService {

    private final PremiumRepository premiumRepository;
    private final OnlinePurchaseRepository onlinePurchaseRepository;
    private final PolicyRepository policyRepository;
    private final PremiumPaymentRepository premiumPaymentRepository;

    public PremiumServiceImpl(PremiumRepository premiumRepository,
                              OnlinePurchaseRepository onlinePurchaseRepository,
                              PolicyRepository policyRepository,
                              PremiumPaymentRepository premiumPaymentRepository) {
        this.premiumRepository = premiumRepository;
        this.onlinePurchaseRepository = onlinePurchaseRepository;
        this.policyRepository = policyRepository;
        this.premiumPaymentRepository = premiumPaymentRepository;
    }

    @Override
    public Premium makePayment(Long premiumId, String method) throws Exception {
        Premium premiumToPay = getPremiumById(premiumId);

        if (!"PENDING".equals(premiumToPay.getStatus())) {
            throw new Exception("This premium is not pending for payment.");
        }

        PremiumPayment payment = new PremiumPayment();
        payment.setPolicyId(premiumToPay.getPolicyId());
        payment.setAmount(premiumToPay.getAmount());
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(PaymentMethod.valueOf(method));
        premiumPaymentRepository.save(payment);

        premiumToPay.setStatus("PAID");
        premiumRepository.save(premiumToPay);

        Policy policy = policyRepository.findById(premiumToPay.getPolicyId())
                .orElseThrow(() -> new Exception("Associated policy not found for premium."));

        if (policy.getRenewalFrequency() != null) {
            Premium nextPremium = new Premium();
            nextPremium.setPolicyId(policy.getId());
            nextPremium.setAmount(policy.getPremiumAmount());
            LocalDate nextDueDate = calculateNextDueDate(premiumToPay.getDueDate(), policy.getRenewalFrequency());
            nextPremium.setDueDate(nextDueDate);
            nextPremium.setStatus("PENDING");
            createPremium(nextPremium);
        }

        return premiumToPay;
    }

    @Override
    public List<Premium> getPremiumsForUser(Long userId) {
        List<Long> policyIds = onlinePurchaseRepository.findByUserId(userId)
                .stream()
                .map(purchase -> purchase.getPolicy().getId())
                .collect(Collectors.toList());

        return policyIds.stream()
                .flatMap(policyId -> premiumRepository.findByPolicyId(policyId).stream())
                .collect(Collectors.toList());
    }

    private LocalDate calculateNextDueDate(LocalDate currentDate, RenewalFrequency frequency) {
        if (currentDate == null) {
            currentDate = LocalDate.now(); // Fallback to current date
        }
        switch (frequency) {
            case MONTHLY: return currentDate.plusMonths(1);
            case QUARTERLY: return currentDate.plusMonths(3);
            case HALF_YEARLY: return currentDate.plusMonths(6);
            case YEARLY: return currentDate.plusYears(1);
            default: return currentDate.plusYears(1);
        }
    }

    @Override
    public List<Premium> getPremiumSchedule(Long policyId) {
        return premiumRepository.findByPolicyId(policyId);
    }

    @Override
    public List<Premium> getPaymentHistory(Long policyId) {
        return premiumRepository.findByPolicyId(policyId);
    }

    @Override
    public Premium createPremium(Premium premium) {
        return premiumRepository.save(premium);
    }

    @Override
    public List<Premium> getAllPremiums() {
        return premiumRepository.findAll();
    }

    @Override
    public Premium getPremiumById(Long id) throws Exception {
        return premiumRepository.findById(id)
                .orElseThrow(() -> new Exception("Premium not found with id: " + id));
    }

    @Override
    public Premium updatePremium(Long id, Premium premiumDetails) throws Exception {
        Premium premium = getPremiumById(id);
        premium.setPolicyId(premiumDetails.getPolicyId());
        premium.setAmount(premiumDetails.getAmount());
        premium.setDueDate(premiumDetails.getDueDate());
        premium.setStatus(premiumDetails.getStatus());
        return premiumRepository.save(premium);
    }

    @Override
    public void deletePremium(Long id) throws Exception {
        if (!premiumRepository.existsById(id)) {
            throw new Exception("Premium not found with id: " + id);
        }
        premiumRepository.deleteById(id);
    }

}