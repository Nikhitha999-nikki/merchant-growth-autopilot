package com.merchantgrowth.merchant_growth_autopilot.dto;

import java.util.List;

public class OpportunityAnalysisResponse {

    private Long opportunityId;
    private String segment;

    private double currentRate;
    private double baselineRate;
    private double gap;

    private long checkoutStarted;
    private long paymentSuccess;
    private long paymentFailed;

    private double successRate;
    private double baselineSuccessRate;

    private List<String> evidence;

    public OpportunityAnalysisResponse(
            Long opportunityId,
            String segment,
            double currentRate,
            double baselineRate,
            double gap,
            long checkoutStarted,
            long paymentSuccess,
            long paymentFailed,
            double successRate,
            double baselineSuccessRate,
            List<String> evidence) {

        this.opportunityId = opportunityId;
        this.segment = segment;
        this.currentRate = currentRate;
        this.baselineRate = baselineRate;
        this.gap = gap;
        this.checkoutStarted = checkoutStarted;
        this.paymentSuccess = paymentSuccess;
        this.paymentFailed = paymentFailed;
        this.successRate = successRate;
        this.baselineSuccessRate = baselineSuccessRate;
        this.evidence = evidence;
    }

    public Long getOpportunityId() {
        return opportunityId;
    }

    public String getSegment() {
        return segment;
    }

    public double getCurrentRate() {
        return currentRate;
    }

    public double getBaselineRate() {
        return baselineRate;
    }

    public double getGap() {
        return gap;
    }

    public long getCheckoutStarted() {
        return checkoutStarted;
    }

    public long getPaymentSuccess() {
        return paymentSuccess;
    }

    public long getPaymentFailed() {
        return paymentFailed;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public double getBaselineSuccessRate() {
        return baselineSuccessRate;
    }

    public List<String> getEvidence() {
        return evidence;
    }
}