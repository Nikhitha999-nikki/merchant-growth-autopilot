package com.merchantgrowth.merchant_growth_autopilot.agent.tool;

import com.merchantgrowth.merchant_growth_autopilot.entity.ImpactMeasurement;
import com.merchantgrowth.merchant_growth_autopilot.service.ImpactMeasurementService;
import org.springframework.stereotype.Component;

@Component
public class ImpactMeasurementTool {

    private final ImpactMeasurementService impactMeasurementService;

    public ImpactMeasurementTool(
            ImpactMeasurementService impactMeasurementService) {

        this.impactMeasurementService = impactMeasurementService;
    }

    public ImpactMeasurement measure(Long recommendationId) {

        return impactMeasurementService.measureImpact(recommendationId);
    }
}