package com.merchantgrowth.merchant_growth_autopilot.controller;

import com.merchantgrowth.merchant_growth_autopilot.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/orders")
    public Map<String, Long> getTotalOrders() {
        return Map.of(
                "totalOrders",
                analyticsService.getTotalOrders()
        );
    }

    @GetMapping("/revenue")
    public Map<String, BigDecimal> getTotalRevenue() {
        return Map.of(
                "totalRevenue",
                analyticsService.getTotalRevenue()
        );
    }

    @GetMapping("/customers")
    public Map<String, Long> getTotalCustomers() {
        return Map.of(
                "totalCustomers",
                analyticsService.getTotalCustomers()
        );
    }

    @GetMapping("/aov")
    public Map<String, BigDecimal> getAverageOrderValue() {
        return Map.of(
                "averageOrderValue",
                analyticsService.getAverageOrderValue()
        );
    }
}