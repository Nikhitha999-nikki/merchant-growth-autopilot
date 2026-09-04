package com.merchantgrowth.merchant_growth_autopilot.agent.tool;

import com.merchantgrowth.merchant_growth_autopilot.dto.OpportunityAnalysisResponse;
import com.merchantgrowth.merchant_growth_autopilot.service.OpportunityAnalysisService;
import org.springframework.stereotype.Component;

@Component
public class OpportunityAnalysisTool {

    private final OpportunityAnalysisService opportunityAnalysisService;

    public OpportunityAnalysisTool(
            OpportunityAnalysisService opportunityAnalysisService) {

        this.opportunityAnalysisService = opportunityAnalysisService;
    }

    public OpportunityAnalysisResponse analyze(Long opportunityId) {

        return opportunityAnalysisService.analyze(opportunityId);
    }
}