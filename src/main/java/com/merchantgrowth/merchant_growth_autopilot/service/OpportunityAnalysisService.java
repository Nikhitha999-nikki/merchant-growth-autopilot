package com.merchantgrowth.merchant_growth_autopilot.service;

import com.merchantgrowth.merchant_growth_autopilot.dto.OpportunityAnalysisResponse;
import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import com.merchantgrowth.merchant_growth_autopilot.repository.CommerceEventRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.GrowthOpportunityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpportunityAnalysisService {

    private final GrowthOpportunityRepository opportunityRepository;
    private final CommerceEventRepository commerceEventRepository;

    public OpportunityAnalysisService(
            GrowthOpportunityRepository opportunityRepository,
            CommerceEventRepository commerceEventRepository) {

        this.opportunityRepository = opportunityRepository;
        this.commerceEventRepository = commerceEventRepository;
    }

    public OpportunityAnalysisResponse analyze(Long opportunityId) {

        GrowthOpportunity opportunity =
                opportunityRepository.findById(opportunityId)
                        .orElseThrow(() -> new RuntimeException(
                                "Opportunity not found with id: " + opportunityId));

        String segment = opportunity.getSegment();

        long checkoutStarted =
                commerceEventRepository.countByDeviceAndEventType(
                        segment,
                        "CHECKOUT_STARTED");

        long paymentSuccess =
                commerceEventRepository.countByDeviceAndEventType(
                        segment,
                        "PAYMENT_SUCCESS");

        long paymentFailed =
                commerceEventRepository.countByDeviceAndEventType(
                        segment,
                        "PAYMENT_FAILED");

        double currentRate =
                opportunity.getCurrentRate().doubleValue();

        double baselineRate =
                opportunity.getBaselineRate().doubleValue();

        double gap = baselineRate - currentRate;

        List<String> evidence = List.of(
                checkoutStarted
                        + " " + segment.toLowerCase()
                        + " customers started checkout, but only "
                        + paymentSuccess
                        + " completed payment.",

                "Desktop checkout converts at "
                        + String.format("%.2f", baselineRate)
                        + "%, compared with "
                        + String.format("%.2f", currentRate)
                        + "% on "
                        + segment.toLowerCase()
                        + ".",

                segment.toLowerCase()
                        + " payment completion is "
                        + String.format("%.2f", gap)
                        + " percentage points below desktop.",

                paymentFailed
                        + " "
                        + segment.toLowerCase()
                        + " payment failure event was recorded."
        );

        return new OpportunityAnalysisResponse(
                opportunity.getId(),
                segment,
                currentRate,
                baselineRate,
                gap,
                checkoutStarted,
                paymentSuccess,
                paymentFailed,
                currentRate,
                baselineRate,
                evidence
        );
    }
}