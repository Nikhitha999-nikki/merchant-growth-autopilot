package com.merchantgrowth.merchant_growth_autopilot.service;

import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import com.merchantgrowth.merchant_growth_autopilot.entity.Recommendation;
import com.merchantgrowth.merchant_growth_autopilot.repository.GrowthOpportunityRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendationService {

    private final GrowthOpportunityRepository growthOpportunityRepository;
    private final RecommendationRepository recommendationRepository;

    public RecommendationService(
            GrowthOpportunityRepository growthOpportunityRepository,
            RecommendationRepository recommendationRepository) {

        this.growthOpportunityRepository = growthOpportunityRepository;
        this.recommendationRepository = recommendationRepository;
    }
    public List<Recommendation> generateRecommendations() {

        List<GrowthOpportunity> opportunities =
                growthOpportunityRepository.findAll();

        for (GrowthOpportunity opportunity : opportunities) {

            if ("PAYMENT_CONVERSION".equals(opportunity.getType())
                    && recommendationRepository
                    .findByOpportunityId(opportunity.getId())
                    .isEmpty()) {

                Recommendation recommendation = new Recommendation(
                        opportunity,
                        "Improve mobile checkout experience",
                        "Optimize the mobile checkout and payment flow to improve payment conversion.",
                        "HIGH",
                        "GENERATED",
                        LocalDateTime.now()
                );

                recommendationRepository.save(recommendation);
            }
        }

        return recommendationRepository.findAll();
    }
    public Recommendation approveRecommendation(Long id) {

        Recommendation recommendation =
                recommendationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recommendation not found with id: " + id));

        recommendation.setStatus("APPROVED");

        return recommendationRepository.save(recommendation);
    }
    public Recommendation rejectRecommendation(Long id) {

        Recommendation recommendation =
                recommendationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recommendation not found with id: " + id));

        recommendation.setStatus("REJECTED");

        return recommendationRepository.save(recommendation);
    }
}
