package com.merchantgrowth.merchant_growth_autopilot.repository;

import com.merchantgrowth.merchant_growth_autopilot.entity.ImpactMeasurement;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImpactMeasurementRepository
        extends JpaRepository<ImpactMeasurement, Long> {

    List<ImpactMeasurement> findByRecommendationOrderByMeasuredAtDesc(
            Recommendation recommendation);
}