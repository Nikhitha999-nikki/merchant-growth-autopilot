package com.merchantgrowth.merchant_growth_autopilot.service;

import com.merchantgrowth.merchant_growth_autopilot.repository.CustomerRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AnalyticsService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public AnalyticsService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public BigDecimal getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    public long getTotalCustomers() {
        return customerRepository.count();
    }

    public BigDecimal getAverageOrderValue() {
        return orderRepository.getAverageOrderValue();
    }
}