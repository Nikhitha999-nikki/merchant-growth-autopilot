package com.merchantgrowth.merchant_growth_autopilot.controller;

import com.merchantgrowth.merchant_growth_autopilot.entity.GuardrailDecision;
import com.merchantgrowth.merchant_growth_autopilot.service.GuardrailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guardrails")
public class GuardrailController {

    private final GuardrailService guardrailService;

    public GuardrailController(GuardrailService guardrailService) {
        this.guardrailService = guardrailService;
    }

    @GetMapping("/check/{recommendationId}")
    public GuardrailDecision checkRecommendation(
            @PathVariable Long recommendationId) {

        return guardrailService.checkRecommendation(recommendationId);
    }
}