package com.merchantgrowth.merchant_growth_autopilot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "impact_measurements")
public class ImpactMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recommendation_id", nullable = false)
    private Recommendation recommendation;

    @Column(nullable = false)
    private double beforeRate;

    @Column(nullable = false)
    private double afterRate;

    @Column(nullable = false)
    private double improvement;

    @Column(nullable = false)
    private double estimatedRevenue;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    public ImpactMeasurement() {
    }

    public ImpactMeasurement(
            Recommendation recommendation,
            double beforeRate,
            double afterRate,
            double improvement,
            double estimatedRevenue,
            LocalDateTime measuredAt) {

        this.recommendation = recommendation;
        this.beforeRate = beforeRate;
        this.afterRate = afterRate;
        this.improvement = improvement;
        this.estimatedRevenue = estimatedRevenue;
        this.measuredAt = measuredAt;
    }

    public Long getId() {
        return id;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public double getBeforeRate() {
        return beforeRate;
    }

    public double getAfterRate() {
        return afterRate;
    }

    public double getImprovement() {
        return improvement;
    }

    public double getEstimatedRevenue() {
        return estimatedRevenue;
    }

    public LocalDateTime getMeasuredAt() {
        return measuredAt;
    }
}