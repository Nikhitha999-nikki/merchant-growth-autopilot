package com.merchantgrowth.merchant_growth_autopilot.agent.tool;

import com.merchantgrowth.merchant_growth_autopilot.entity.GuardrailDecision;
import com.merchantgrowth.merchant_growth_autopilot.service.GuardrailService;
import org.springframework.stereotype.Component;

@Component
public class GuardrailTool {

    private final GuardrailService guardrailService;

    public GuardrailTool(GuardrailService guardrailService) {
        this.guardrailService = guardrailService;
    }

    public GuardrailDecision check(Long recommendationId) {
        return guardrailService.checkRecommendation(recommendationId);
    }
}