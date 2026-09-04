package com.merchantgrowth.merchant_growth_autopilot.repository;

import com.merchantgrowth.merchant_growth_autopilot.entity.CommerceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommerceEventRepository extends JpaRepository<CommerceEvent, Long> {

    @Query("""
        SELECT COUNT(e)
        FROM CommerceEvent e
        WHERE e.deviceType = :device
        AND e.eventType = :eventType
    """)
    long countByDeviceAndEventType(
            @Param("device") String device,
            @Param("eventType") String eventType
    );
}