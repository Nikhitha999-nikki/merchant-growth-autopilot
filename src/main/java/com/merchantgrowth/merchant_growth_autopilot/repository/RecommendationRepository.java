package com.merchantgrowth.merchant_growth_autopilot.repository;

import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendationRepository
        extends JpaRepository<Recommendation, Long> {

    Optional<Recommendation> findByOpportunityId(Long opportunityId);
}