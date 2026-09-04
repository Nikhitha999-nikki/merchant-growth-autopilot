package com.merchantgrowth.merchant_growth_autopilot.repository;

import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrowthOpportunityRepository
        extends JpaRepository<GrowthOpportunity, Long> {
    List<GrowthOpportunity> findByTypeAndSegment(
            String type,
            String segment
    );
}