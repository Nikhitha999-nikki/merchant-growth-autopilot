package com.merchantgrowth.merchant_growth_autopilot.repository;

import com.merchantgrowth.merchant_growth_autopilot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}