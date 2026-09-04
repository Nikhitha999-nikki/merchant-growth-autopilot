package com.merchantgrowth.merchant_growth_autopilot.agent;

import com.merchantgrowth.merchant_growth_autopilot.dto.OpportunityAnalysisResponse;
import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;

import java.util.List;

public class RankedOpportunity {

    private final GrowthOpportunity opportunity;
    private final OpportunityAnalysisResponse analysis;
    private final double score;
    private final List<String> reasons;

    public RankedOpportunity(
            GrowthOpportunity opportunity,
            OpportunityAnalysisResponse analysis,
            double score,
            List<String> reasons) {

        this.opportunity = opportunity;
        this.analysis = analysis;
        this.score = score;
        this.reasons = reasons;
    }

    public GrowthOpportunity getOpportunity() {
        return opportunity;
    }

    public OpportunityAnalysisResponse getAnalysis() {
        return analysis;
    }

    public double getScore() {
        return score;
    }

    public List<String> getReasons() {
        return reasons;
    }
}