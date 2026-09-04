package com.merchantgrowth.merchant_growth_autopilot.agent.tool;

import com.merchantgrowth.merchant_growth_autopilot.entity.ActionExecution;
import com.merchantgrowth.merchant_growth_autopilot.service.ActionExecutionService;
import org.springframework.stereotype.Component;

@Component
public class ActionTool {

    private final ActionExecutionService actionExecutionService;

    public ActionTool(ActionExecutionService actionExecutionService) {
        this.actionExecutionService = actionExecutionService;
    }

    public ActionExecution execute(Long recommendationId) {
        return actionExecutionService.executeAction(recommendationId);
    }
}