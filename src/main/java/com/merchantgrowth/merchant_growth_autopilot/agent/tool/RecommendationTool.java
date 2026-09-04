package com.merchantgrowth.merchant_growth_autopilot.agent.tool;

import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import com.merchantgrowth.merchant_growth_autopilot.service.RecommendationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationTool {

    private final RecommendationService recommendationService;

    public RecommendationTool(
            RecommendationService recommendationService) {

        this.recommendationService = recommendationService;
    }

    public List<Recommendation> generateRecommendations() {
        return recommendationService.generateRecommendations();
    }


}