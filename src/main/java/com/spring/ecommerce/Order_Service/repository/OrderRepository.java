package com.spring.ecommerce.Order_Service.repository;

import com.spring.ecommerce.Order_Service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

