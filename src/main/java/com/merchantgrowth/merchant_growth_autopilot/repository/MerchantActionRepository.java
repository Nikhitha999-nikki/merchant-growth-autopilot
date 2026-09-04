package com.merchantgrowth.merchant_growth_autopilot.repository;

import com.merchantgrowth.merchant_growth_autopilot.entity.MerchantAction;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantActionRepository
        extends JpaRepository<MerchantAction, Long> {

    Optional<MerchantAction> findByRecommendation(
            Recommendation recommendation);
}