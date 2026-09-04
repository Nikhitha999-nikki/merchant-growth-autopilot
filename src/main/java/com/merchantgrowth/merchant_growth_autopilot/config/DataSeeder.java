package com.merchantgrowth.merchant_growth_autopilot.config;

import com.merchantgrowth.merchant_growth_autopilot.entity.Customer;
import com.merchantgrowth.merchant_growth_autopilot.entity.Merchant;
import com.merchantgrowth.merchant_growth_autopilot.entity.Order;
import com.merchantgrowth.merchant_growth_autopilot.entity.Product;
import com.merchantgrowth.merchant_growth_autopilot.repository.CustomerRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.MerchantRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.OrderRepository;
import com.merchantgrowth.merchant_growth_autopilot.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            MerchantRepository merchantRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository) {

        return args -> {

            // Avoid inserting duplicate data every time the application starts
            if (merchantRepository.count() > 0) {
                System.out.println("Sample data already exists. Skipping seeding.");
                return;
            }

            // 1. Create Merchant
            Merchant merchant = new Merchant(
                    "Demo Fashion Store",
                    "merchant@example.com"
            );

            merchantRepository.save(merchant);

            // 2. Create Customers
            List<Customer> customers = new ArrayList<>();

            for (int i = 1; i <= 10; i++) {
                Customer customer = new Customer(
                        "Customer " + i,
                        "customer" + i + "@example.com"
                );

                customers.add(customer);
            }

            customerRepository.saveAll(customers);

            // 3. Create Products
            String[] productNames = {
                    "Classic T-Shirt",
                    "Denim Jeans",
                    "Running Shoes",
                    "Casual Shirt",
                    "Hoodie",
                    "Summer Dress",
                    "Leather Wallet",
                    "Backpack",
                    "Sneakers",
                    "Cotton Jacket"
            };

            String[] categories = {
                    "Clothing",
                    "Clothing",
                    "Footwear",
                    "Clothing",
                    "Clothing",
                    "Clothing",
                    "Accessories",
                    "Accessories",
                    "Footwear",
                    "Clothing"
            };

            List<Product> products = new ArrayList<>();

            for (int i = 0; i < 10; i++) {

                BigDecimal price = BigDecimal.valueOf(
                        499 + (i * 300)
                );

                Product product = new Product(
                        productNames[i],
                        price,
                        categories[i]
                );

                products.add(product);
            }

            productRepository.saveAll(products);

            // 4. Create Orders
            Random random = new Random(42);

            String[] statuses = {
                    "COMPLETED",
                    "COMPLETED",
                    "COMPLETED",
                    "CANCELLED"
            };

            List<Order> orders = new ArrayList<>();

            for (int i = 1; i <= 50; i++) {

                Customer customer =
                        customers.get(random.nextInt(customers.size()));

                BigDecimal amount = BigDecimal.valueOf(
                        500 + random.nextInt(4501)
                );

                String status =
                        statuses[random.nextInt(statuses.length)];

                LocalDateTime createdAt =
                        LocalDateTime.now().minusDays(
                                random.nextInt(30)
                        );

                Order order = new Order(
                        customer,
                        amount,
                        status,
                        createdAt
                );

                orders.add(order);
            }

            orderRepository.saveAll(orders);

            System.out.println("======================================");
            System.out.println("Synthetic data seeded successfully!");
            System.out.println("Merchants : " + merchantRepository.count());
            System.out.println("Customers : " + customerRepository.count());
            System.out.println("Products  : " + productRepository.count());
            System.out.println("Orders    : " + orderRepository.count());
            System.out.println("======================================");
        };
    }
}