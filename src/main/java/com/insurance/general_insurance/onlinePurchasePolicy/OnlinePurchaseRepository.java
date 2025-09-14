package com.insurance.general_insurance.onlinePurchasePolicy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OnlinePurchaseRepository extends JpaRepository<OnlinePurchase, Long> {
    List<OnlinePurchase> findByUserId(Long userId);
}