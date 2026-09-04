package com.merchantgrowth.merchant_growth_autopilot.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "growth_opportunities")
public class GrowthOpportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String segment;

    @Column(precision = 5, scale = 2)
    private BigDecimal currentRate;

    @Column(precision = 5, scale = 2)
    private BigDecimal baselineRate;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    public GrowthOpportunity() {
    }

    public GrowthOpportunity(
            String type,
            String title,
            String description,
            String segment,
            BigDecimal currentRate,
            BigDecimal baselineRate,
            String severity,
            String status,
            LocalDateTime detectedAt) {

        this.type = type;
        this.title = title;
        this.description = description;
        this.segment = segment;
        this.currentRate = currentRate;
        this.baselineRate = baselineRate;
        this.severity = severity;
        this.status = status;
        this.detectedAt = detectedAt;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
    }

    public BigDecimal getBaselineRate() {
        return baselineRate;
    }

    public void setBaselineRate(BigDecimal baselineRate) {
        this.baselineRate = baselineRate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }
}