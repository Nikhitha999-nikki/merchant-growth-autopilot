package com.merchantgrowth.merchant_growth_autopilot.repository;

import com.merchantgrowth.merchant_growth_autopilot.entity.ActionExecution;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionExecutionRepository
        extends JpaRepository<ActionExecution, Long> {

    List<ActionExecution> findByRecommendationOrderByExecutedAtDesc(
            Recommendation recommendation);
}