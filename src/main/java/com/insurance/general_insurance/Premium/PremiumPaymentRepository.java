package com.insurance.general_insurance.Premium;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumPaymentRepository extends JpaRepository<PremiumPayment, Long> {
}
