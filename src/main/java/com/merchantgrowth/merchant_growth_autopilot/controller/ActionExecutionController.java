package com.merchantgrowth.merchant_growth_autopilot.controller;

import com.merchantgrowth.merchant_growth_autopilot.entity.ActionExecution;
import com.merchantgrowth.merchant_growth_autopilot.service.ActionExecutionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
public class ActionExecutionController {

    private final ActionExecutionService actionExecutionService;

    public ActionExecutionController(
            ActionExecutionService actionExecutionService) {

        this.actionExecutionService = actionExecutionService;
    }

    @PostMapping("/execute/{recommendationId}")
    public ActionExecution executeAction(
            @PathVariable Long recommendationId) {

        return actionExecutionService.executeAction(recommendationId);
    }

    @GetMapping
    public List<ActionExecution> getAllExecutions() {
        return actionExecutionService.getAllExecutions();
    }
}