package com.merchantgrowth.merchant_growth_autopilot.controller;

import com.merchantgrowth.merchant_growth_autopilot.entity.ImpactMeasurement;
import com.merchantgrowth.merchant_growth_autopilot.service.ImpactMeasurementService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/impact")
public class ImpactMeasurementController {

    private final ImpactMeasurementService impactMeasurementService;

    public ImpactMeasurementController(
            ImpactMeasurementService impactMeasurementService) {

        this.impactMeasurementService = impactMeasurementService;
    }

    @PostMapping("/measure/{recommendationId}")
    public ImpactMeasurement measureImpact(
            @PathVariable Long recommendationId) {

        return impactMeasurementService.measureImpact(recommendationId);
    }

    @GetMapping
    public List<ImpactMeasurement> getAllImpacts() {
        return impactMeasurementService.getAllImpacts();
    }
}