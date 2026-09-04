package com.merchantgrowth.merchant_growth_autopilot.service;

import com.merchantgrowth.merchant_growth_autopilot.entity.GrowthOpportunity;
import com.merchantgrowth.merchant_growth_autopilot.repository.GrowthOpportunityRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.CommerceEventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OpportunityService {

    private final CommerceEventRepository commerceEventRepository;
    private final GrowthOpportunityRepository growthOpportunityRepository;

    public OpportunityService(
            CommerceEventRepository commerceEventRepository,
            GrowthOpportunityRepository growthOpportunityRepository) {

        this.commerceEventRepository = commerceEventRepository;
        this.growthOpportunityRepository = growthOpportunityRepository;
    }

    public List<GrowthOpportunity> detectOpportunities() {

        long mobileCheckout =
                commerceEventRepository.countByDeviceAndEventType(
                        "MOBILE", "CHECKOUT_STARTED");

        long mobileSuccess =
                commerceEventRepository.countByDeviceAndEventType(
                        "MOBILE", "PAYMENT_SUCCESS");

        long desktopCheckout =
                commerceEventRepository.countByDeviceAndEventType(
                        "DESKTOP", "CHECKOUT_STARTED");

        long desktopSuccess =
                commerceEventRepository.countByDeviceAndEventType(
                        "DESKTOP", "PAYMENT_SUCCESS");

        if (mobileCheckout == 0 || desktopCheckout == 0) {
            return List.of();
        }

        BigDecimal mobileRate = BigDecimal.valueOf(mobileSuccess)
                .multiply(BigDecimal.valueOf(100))
                .divide(
                        BigDecimal.valueOf(mobileCheckout),
                        2,
                        RoundingMode.HALF_UP
                );

        BigDecimal desktopRate = BigDecimal.valueOf(desktopSuccess)
                .multiply(BigDecimal.valueOf(100))
                .divide(
                        BigDecimal.valueOf(desktopCheckout),
                        2,
                        RoundingMode.HALF_UP
                );

        if (mobileRate.compareTo(desktopRate) < 0) {

            /*
             * Check whether this opportunity already exists.
             * This prevents duplicate opportunities every time
             * the detection process runs.
             */
            List<GrowthOpportunity> existingOpportunities =
                    growthOpportunityRepository.findByTypeAndSegment(
                            "PAYMENT_CONVERSION",
                            "MOBILE"
                    );

            if (!existingOpportunities.isEmpty()) {

                GrowthOpportunity existing =
                        existingOpportunities.get(0);

                return List.of(existing);
            }

            GrowthOpportunity opportunity = new GrowthOpportunity(
                    "PAYMENT_CONVERSION",
                    "Mobile payment conversion is low",
                    "Mobile users have a significantly lower payment conversion rate than desktop users.",
                    "MOBILE",
                    mobileRate,
                    desktopRate,
                    "HIGH",
                    "DETECTED",
                    LocalDateTime.now()
            );

            return List.of(
                    growthOpportunityRepository.save(opportunity)
            );
        }

        return List.of();
    }
}