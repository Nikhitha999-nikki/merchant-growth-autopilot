package com.merchantgrowth.merchant_growth_autopilot.agent.tool;

import com.merchantgrowth.merchant_growth_autopilot.agent.RankedOpportunity;
import com.merchantgrowth.merchant_growth_autopilot.dto.OpportunityAnalysisResponse;
import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class OpportunityRankingTool {

    private final OpportunityAnalysisTool opportunityAnalysisTool;

    public OpportunityRankingTool(
            OpportunityAnalysisTool opportunityAnalysisTool) {

        this.opportunityAnalysisTool = opportunityAnalysisTool;
    }

    public List<RankedOpportunity> rank(
            List<GrowthOpportunity> opportunities) {

        List<RankedOpportunity> ranked = new ArrayList<>();

        for (GrowthOpportunity opportunity : opportunities) {

            OpportunityAnalysisResponse analysis =
                    opportunityAnalysisTool.analyze(opportunity.getId());

            double severityScore =
                    calculateSeverityScore(opportunity.getSeverity());

            double gapScore =
                    Math.min(Math.max(analysis.getGap(), 0), 100) * 0.40;

            double volumeScore =
                    Math.min(analysis.getCheckoutStarted(), 20) / 20.0 * 20;

            double totalScore =
                    severityScore + gapScore + volumeScore;

            totalScore = Math.round(totalScore * 100.0) / 100.0;

            List<String> reasons = List.of(
                    "Severity contributes " +
                            String.format("%.2f", severityScore) +
                            " points.",
                    "Performance gap contributes " +
                            String.format("%.2f", gapScore) +
                            " points.",
                    "Checkout volume contributes " +
                            String.format("%.2f", volumeScore) +
                            " points."
            );

            ranked.add(
                    new RankedOpportunity(
                            opportunity,
                            analysis,
                            totalScore,
                            reasons
                    )
            );
        }

        ranked.sort(
                Comparator.comparingDouble(
                        RankedOpportunity::getScore
                ).reversed()
        );

        return ranked;
    }

    private double calculateSeverityScore(String severity) {

        if (severity == null) {
            return 0;
        }

        return switch (severity.toUpperCase()) {
            case "HIGH" -> 40;
            case "MEDIUM" -> 25;
            case "LOW" -> 10;
            default -> 0;
        };
    }
}