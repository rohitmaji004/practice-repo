package com.insurance.general_insurance.onlinepurchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlinePurchaseRepository extends JpaRepository<OnlinePurchase, Long> {
}