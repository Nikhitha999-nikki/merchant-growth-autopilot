package com.merchantgrowth.merchant_growth_autopilot.agent.tool;

import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import com.merchantgrowth.merchant_growth_autopilot.repository.GrowthOpportunityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpportunityTool {

    private final GrowthOpportunityRepository growthOpportunityRepository;

    public OpportunityTool(
            GrowthOpportunityRepository growthOpportunityRepository) {

        this.growthOpportunityRepository = growthOpportunityRepository;
    }

    public List<GrowthOpportunity> findOpportunities() {
        return growthOpportunityRepository.findAll();
    }
}