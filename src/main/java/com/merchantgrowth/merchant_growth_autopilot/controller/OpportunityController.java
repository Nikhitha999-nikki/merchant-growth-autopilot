package com.merchantgrowth.merchant_growth_autopilot.controller;

import com.merchantgrowth.merchant_growth_autopilot.dto.OpportunityAnalysisResponse;
import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import com.merchantgrowth.merchant_growth_autopilot.service.OpportunityAnalysisService;
import com.merchantgrowth.merchant_growth_autopilot.service.OpportunityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    private final OpportunityService opportunityService;
    private final OpportunityAnalysisService opportunityAnalysisService;

    public OpportunityController(
            OpportunityService opportunityService,
            OpportunityAnalysisService opportunityAnalysisService) {

        this.opportunityService = opportunityService;
        this.opportunityAnalysisService = opportunityAnalysisService;
    }

    @GetMapping
    public List<GrowthOpportunity> getOpportunities() {
        return opportunityService.detectOpportunities();
    }

    @GetMapping("/{id}/analysis")
    public OpportunityAnalysisResponse analyzeOpportunity(
            @PathVariable Long id) {

        return opportunityAnalysisService.analyze(id);
    }
}