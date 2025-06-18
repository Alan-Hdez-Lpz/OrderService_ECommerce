package com.spring.ecommerce.Order_Service.service;

import com.spring.ecommerce.Order_Service.model.Order;
import com.spring.ecommerce.Order_Service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    @CircuitBreaker(name = "orderService", fallbackMethod = "productFallback")
    public Order createOrder(Order order) {
        // Validate product availability
        boolean isProductAvailable = Boolean.TRUE.equals(restTemplate.getForObject(productServiceUrl + "/products/isAvailable/" + order.getProductId(), Boolean.class));
        if (!isProductAvailable) {
            throw new RuntimeException("Product not available");
        }
        System.out.println("Product is available");
        order.setStatus("Pending");
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not exist");
    }

    public Order updateOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            switch (order.getStatus()) {
                case "Pending" -> order.setStatus("Payed");
                case "Payed" -> order.setStatus("Shipped");
                case "Shipped" -> order.setStatus("Delivered");
            }
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not exist");
    }

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public Order payOrder(Long id) {
        boolean response = Boolean.TRUE.equals(restTemplate.getForObject(paymentServiceUrl + "/payments/" + id, Boolean.class));
        if(response) {
            System.out.println("Payment successful for order " + id);
            return updateOrderStatus(id);
        } else {
            throw new RuntimeException("Payment failed for order " + id);
        }
    }

    // Fallback method
    public Order paymentFallback(Long orderId, Throwable ex) {
        System.out.println("Payment service is currently unavailable. Order " + orderId + " marked as pending payment.");
        return orderRepository.findById(orderId).orElse(null);
    }

    // Fallback method
    public Order productFallback(Throwable ex) {
        System.out.println("Product service is currently unavailable. Is it not possible to validate if the product is available");
        return null;
    }
}

