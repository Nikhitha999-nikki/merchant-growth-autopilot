package com.merchantgrowth.merchant_growth_autopilot.controller;

import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import com.merchantgrowth.merchant_growth_autopilot.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService) {

        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<Recommendation> getRecommendations() {
        return recommendationService.generateRecommendations();
    }

    @PutMapping("/{id}/approve")
    public Recommendation approveRecommendation(@PathVariable Long id) {
        return recommendationService.approveRecommendation(id);
    }

    @PutMapping("/{id}/reject")
    public Recommendation rejectRecommendation(@PathVariable Long id) {
        return recommendationService.rejectRecommendation(id);
    }
}