package com.merchantgrowth.merchant_growth_autopilot.service;

import com.merchantgrowth.merchant_growth_autopilot.entity.ActionExecution;
import com.merchantgrowth.merchant_growth_autopilot.entity.GuardrailDecision;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import com.merchantgrowth.merchant_growth_autopilot.repository.ActionExecutionRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.GuardrailDecisionRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActionExecutionService {

    private final RecommendationRepository recommendationRepository;
    private final GuardrailDecisionRepository guardrailDecisionRepository;
    private final ActionExecutionRepository actionExecutionRepository;

    public ActionExecutionService(
            RecommendationRepository recommendationRepository,
            GuardrailDecisionRepository guardrailDecisionRepository,
            ActionExecutionRepository actionExecutionRepository) {

        this.recommendationRepository = recommendationRepository;
        this.guardrailDecisionRepository = guardrailDecisionRepository;
        this.actionExecutionRepository = actionExecutionRepository;
    }

    public ActionExecution executeAction(Long recommendationId) {

        Recommendation recommendation =
                recommendationRepository.findById(recommendationId)
                        .orElseThrow(() -> new RuntimeException(
                                "Recommendation not found with id: "
                                        + recommendationId));

        /*
         * Step 1: Human approval check
         */
        if (!"APPROVED".equalsIgnoreCase(recommendation.getStatus())) {

            return saveBlockedAction(
                    recommendation,
                    "Human approval is required before executing this action."
            );
        }

        /*
         * Step 2: Get latest guardrail decision
         */
        List<GuardrailDecision> decisions =
                guardrailDecisionRepository
                        .findByRecommendationOrderByCheckedAtDesc(
                                recommendation);

        if (decisions.isEmpty()) {

            return saveBlockedAction(
                    recommendation,
                    "Guardrail check is required before executing this action."
            );
        }

        GuardrailDecision latestDecision = decisions.get(0);

        /*
         * Step 3: Guardrail validation
         */
        if (!latestDecision.isAllowed()) {

            return saveBlockedAction(
                    recommendation,
                    "Action blocked by guardrail: "
                            + latestDecision.getReason()
            );
        }

        /*
         * Step 4: Execute business action
         *
         * For MVP we simulate the action.
         */
        String actionType = "MOBILE_CHECKOUT_OPTIMIZATION";

        String result =
                "Mobile checkout optimization action executed successfully.";

        ActionExecution execution =
                new ActionExecution(
                        recommendation,
                        actionType,
                        "EXECUTED",
                        result,
                        LocalDateTime.now()
                );

        return actionExecutionRepository.save(execution);
    }

    private ActionExecution saveBlockedAction(
            Recommendation recommendation,
            String reason) {

        ActionExecution execution =
                new ActionExecution(
                        recommendation,
                        "MOBILE_CHECKOUT_OPTIMIZATION",
                        "BLOCKED",
                        reason,
                        LocalDateTime.now()
                );

        return actionExecutionRepository.save(execution);
    }

    public List<ActionExecution> getAllExecutions() {
        return actionExecutionRepository.findAll();
    }
}