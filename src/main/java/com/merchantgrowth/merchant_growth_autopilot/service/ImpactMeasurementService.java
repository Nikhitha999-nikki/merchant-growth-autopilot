package com.merchantgrowth.merchant_growth_autopilot.service;

import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import com.merchantgrowth.merchant_growth_autopilot.entity.ImpactMeasurement;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import com.merchantgrowth.merchant_growth_autopilot.repository.ImpactMeasurementRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImpactMeasurementService {

    private final RecommendationRepository recommendationRepository;
    private final ImpactMeasurementRepository impactRepository;

    public ImpactMeasurementService(
            RecommendationRepository recommendationRepository,
            ImpactMeasurementRepository impactRepository) {

        this.recommendationRepository = recommendationRepository;
        this.impactRepository = impactRepository;
    }

    public ImpactMeasurement measureImpact(Long recommendationId) {

        Recommendation recommendation =
                recommendationRepository.findById(recommendationId)
                        .orElseThrow(() -> new RuntimeException(
                                "Recommendation not found with id: " + recommendationId));

        GrowthOpportunity opportunity = recommendation.getOpportunity();

        double beforeRate =
                opportunity.getCurrentRate().doubleValue();

        double baselineRate =
                opportunity.getBaselineRate().doubleValue();

        /*
         * Current action is a sandbox optimization.
         * We model a conservative 25% recovery of the gap
         * between current performance and baseline.
         */
        double gap = baselineRate - beforeRate;

        double afterRate =
                beforeRate + (gap * 0.25);

        double improvement =
                afterRate - beforeRate;

        /*
         * Estimated revenue impact for the prototype.
         * Uses 50 processed orders as the current merchant volume.
         */
        double estimatedRevenue =
                (improvement / 100.0) * 50 * 1000;

        ImpactMeasurement measurement =
                new ImpactMeasurement(
                        recommendation,
                        beforeRate,
                        afterRate,
                        improvement,
                        estimatedRevenue,
                        LocalDateTime.now()
                );

        return impactRepository.save(measurement);
    }

    public List<ImpactMeasurement> getAllImpacts() {
        return impactRepository.findAll();
    }
}