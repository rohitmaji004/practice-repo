package com.insurance.general_insurance.Premium;

import java.util.List;

public interface PremiumService {
    List<Premium> getPremiumSchedule(Long policyId);

    Premium makePayment(Long premiumId, String method) throws Exception;

    List<Premium> getPaymentHistory(Long policyId);
    Premium createPremium(Premium premium);
    List<Premium> getAllPremiums();
    Premium getPremiumById(Long id) throws Exception;
    Premium updatePremium(Long id, Premium premiumDetails) throws Exception;
    void deletePremium(Long id) throws Exception;
    List<Premium> getPremiumsForUser(Long userId);
}

