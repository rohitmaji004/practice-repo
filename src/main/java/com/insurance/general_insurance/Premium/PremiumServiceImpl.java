package com.insurance.general_insurance.Premium;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PremiumServiceImpl implements PremiumService {

    private final PremiumRepository premiumRepository;

    public PremiumServiceImpl(PremiumRepository premiumRepository) {
        this.premiumRepository = premiumRepository;
    }

    @Override
    public List<Premium> getPremiumSchedule(Long policyId) {
        return premiumRepository.findByPolicyId(policyId);
    }

    @Override
    public Premium makePayment(Long policyId, Double amount, String method) {
        PremiumPayment payment = new PremiumPayment();
        payment.setPolicyId(policyId);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(PaymentMethod.valueOf(method));

        List<Premium> premiums = premiumRepository.findByPolicyId(policyId);
        for (Premium premium : premiums) {
            if (premium.getAmount().equals(amount) && "PENDING".equals(premium.getStatus())) {
                premium.setStatus("PAID");
                return premiumRepository.save(premium);
            }
        }
        return null;
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