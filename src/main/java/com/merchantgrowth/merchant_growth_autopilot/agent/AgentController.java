package com.merchantgrowth.merchant_growth_autopilot.agent;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentOrchestrator agentOrchestrator;

    public AgentController(AgentOrchestrator agentOrchestrator) {
        this.agentOrchestrator = agentOrchestrator;
    }

    @PostMapping("/run")
    public Map<String, Object> runAgent(
            @RequestBody Map<String, String> request) {

        String goal = request.getOrDefault(
                "goal",
                "Find the most important merchant growth opportunity and determine the next action."
        );

        return agentOrchestrator.run(goal);
    }
}