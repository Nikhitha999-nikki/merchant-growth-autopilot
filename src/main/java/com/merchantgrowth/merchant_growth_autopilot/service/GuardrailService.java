package com.merchantgrowth.merchant_growth_autopilot.service;

import com.merchantgrowth.merchant_growth_autopilot.entity.GuardrailDecision;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import com.merchantgrowth.merchant_growth_autopilot.repository.GuardrailDecisionRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GuardrailService {

    private final RecommendationRepository recommendationRepository;
    private final GuardrailDecisionRepository guardrailDecisionRepository;

    public GuardrailService(
            RecommendationRepository recommendationRepository,
            GuardrailDecisionRepository guardrailDecisionRepository) {

        this.recommendationRepository = recommendationRepository;
        this.guardrailDecisionRepository = guardrailDecisionRepository;
    }

    public GuardrailDecision checkRecommendation(Long recommendationId) {

        Recommendation recommendation =
                recommendationRepository.findById(recommendationId)
                        .orElseThrow(() -> new RuntimeException(
                                "Recommendation not found with id: "
                                        + recommendationId));

        String decision;
        String reason;

        // Rule 1: Rejected recommendations are always blocked
        if ("REJECTED".equalsIgnoreCase(recommendation.getStatus())) {

            decision = "BLOCKED";
            reason = "Recommendation was rejected by the merchant.";

        }
        // Rule 2: Recommendation must be approved
        else if (!"APPROVED".equalsIgnoreCase(recommendation.getStatus())) {

            decision = "BLOCKED";
            reason = "Human approval is required before this recommendation can be executed.";

        }
        // Rule 3: Approved high-priority recommendations can proceed
        else if ("HIGH".equalsIgnoreCase(recommendation.getPriority())) {

            decision = "ALLOWED";
            reason = "High-priority recommendation has explicit human approval.";

        }
        // Rule 4: Other approved recommendations
        else {

            decision = "ALLOWED";
            reason = "Recommendation satisfies the current policy rules.";
        }

        boolean allowed = "ALLOWED".equalsIgnoreCase(decision);
        GuardrailDecision guardrailDecision =
                new GuardrailDecision(
                        recommendation,
                        decision,
                        reason,
                        LocalDateTime.now(),
                        allowed
                );

        return guardrailDecisionRepository.save(guardrailDecision);
    }
}