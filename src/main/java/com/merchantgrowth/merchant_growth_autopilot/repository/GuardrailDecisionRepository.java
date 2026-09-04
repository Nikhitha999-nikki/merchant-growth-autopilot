package com.merchantgrowth.merchant_growth_autopilot.repository;

import com.merchantgrowth.merchant_growth_autopilot.entity.GuardrailDecision;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuardrailDecisionRepository
        extends JpaRepository<GuardrailDecision, Long> {

    List<GuardrailDecision> findByRecommendationOrderByCheckedAtDesc(
            Recommendation recommendation);
}