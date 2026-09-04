package com.merchantgrowth.merchant_growth_autopilot.agent;

import com.merchantgrowth.merchant_growth_autopilot.dto.OpportunityAnalysisResponse;
import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import com.merchantgrowth.merchant_growth_autopilot.agent.tool.ActionTool;
import com.merchantgrowth.merchant_growth_autopilot.agent.tool.GuardrailTool;
import com.merchantgrowth.merchant_growth_autopilot.agent.tool.ImpactMeasurementTool;
import com.merchantgrowth.merchant_growth_autopilot.agent.tool.OpportunityRankingTool;
import com.merchantgrowth.merchant_growth_autopilot.agent.tool.OpportunityTool;
import com.merchantgrowth.merchant_growth_autopilot.agent.tool.RecommendationTool;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgentOrchestrator {

    private final OpportunityTool opportunityTool;
    private final OpportunityRankingTool opportunityRankingTool;
    private final RecommendationTool recommendationTool;
    private final GuardrailTool guardrailTool;
    private final ActionTool actionTool;
    private final ImpactMeasurementTool impactMeasurementTool;

    public AgentOrchestrator(
            OpportunityTool opportunityTool,
            OpportunityRankingTool opportunityRankingTool,
            RecommendationTool recommendationTool,
            GuardrailTool guardrailTool,
            ActionTool actionTool,
            ImpactMeasurementTool impactMeasurementTool) {

        this.opportunityTool = opportunityTool;
        this.opportunityRankingTool = opportunityRankingTool;
        this.recommendationTool = recommendationTool;
        this.guardrailTool = guardrailTool;
        this.actionTool = actionTool;
        this.impactMeasurementTool = impactMeasurementTool;
    }

    public Map<String, Object> run(String goal) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("goal", goal);
        response.put("agent", "Merchant Growth Autopilot Agent");

        List<GrowthOpportunity> opportunities =
                opportunityTool.findOpportunities();

        response.put("opportunitiesFound", opportunities.size());

        if (opportunities.isEmpty()) {
            response.put("status", "NO_OPPORTUNITY");
            response.put(
                    "message",
                    "No growth opportunities were detected."
            );
            return response;
        }

        List<RankedOpportunity> rankedOpportunities =
                opportunityRankingTool.rank(opportunities);

        RankedOpportunity topRanked =
                rankedOpportunities.get(0);

        GrowthOpportunity opportunity =
                topRanked.getOpportunity();

        OpportunityAnalysisResponse analysis =
                topRanked.getAnalysis();

        response.put("opportunityId", opportunity.getId());
        response.put("opportunityType", opportunity.getType());
        response.put("opportunityTitle", opportunity.getTitle());
        response.put("opportunitySeverity", opportunity.getSeverity());
        response.put("opportunitySegment", opportunity.getSegment());
        response.put("opportunityScore", topRanked.getScore());
        response.put("rankingReasons", topRanked.getReasons());
        response.put("rankedOpportunities", rankedOpportunities.size());

        response.put("why", analysis.getEvidence());
        response.put("currentRate", analysis.getCurrentRate());
        response.put("baselineRate", analysis.getBaselineRate());
        response.put("gap", analysis.getGap());
        response.put("checkoutStarted", analysis.getCheckoutStarted());
        response.put("paymentSuccess", analysis.getPaymentSuccess());
        response.put("paymentFailed", analysis.getPaymentFailed());

        List<Recommendation> recommendations =
                recommendationTool.generateRecommendations();

        Recommendation recommendation =
                recommendations.stream()
                        .filter(r ->
                                r.getOpportunity().getId()
                                        .equals(opportunity.getId()))
                        .findFirst()
                        .orElse(null);

        if (recommendation == null) {
            response.put("status", "NO_RECOMMENDATION");
            response.put(
                    "message",
                    "No recommendation could be generated for the selected opportunity."
            );
            return response;
        }

        response.put(
                "recommendationId",
                recommendation.getId()
        );

        response.put(
                "recommendation",
                recommendation.getTitle()
        );

        response.put(
                "recommendationStatus",
                recommendation.getStatus()
        );

        var guardrail =
                guardrailTool.check(
                        recommendation.getId()
                );

        response.put(
                "guardrailDecision",
                guardrail.getDecision()
        );

        response.put(
                "guardrailReason",
                guardrail.getReason()
        );

        var action =
                actionTool.execute(
                        recommendation.getId()
                );

        response.put(
                "actionType",
                action.getActionType()
        );

        response.put(
                "executionStatus",
                action.getStatus()
        );

        response.put(
                "executionResult",
                action.getResult()
        );

        if ("EXECUTED".equalsIgnoreCase(action.getStatus())) {

            var impact =
                    impactMeasurementTool.measure(
                            recommendation.getId()
                    );

            response.put(
                    "impactBeforeRate",
                    impact.getBeforeRate()
            );

            response.put(
                    "impactAfterRate",
                    impact.getAfterRate()
            );

            response.put(
                    "impactImprovement",
                    impact.getImprovement()
            );

            response.put(
                    "estimatedRevenue",
                    impact.getEstimatedRevenue()
            );

            response.put(
                    "status",
                    "ACTION_EXECUTED"
            );

            response.put(
                    "message",
                    "The growth opportunity was analyzed, ranked, explained using evidence, approved, validated by guardrails, executed, and its estimated business impact was measured."
            );

        } else {

            response.put(
                    "status",
                    "ACTION_BLOCKED"
            );

            response.put(
                    "message",
                    "The recommended action was blocked by the approval or guardrail policy."
            );
        }

        return response;
    }
}