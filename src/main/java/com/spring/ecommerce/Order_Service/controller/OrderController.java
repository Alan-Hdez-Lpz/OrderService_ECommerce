package com.spring.ecommerce.Order_Service.controller;

import com.spring.ecommerce.Order_Service.model.Order;
import com.spring.ecommerce.Order_Service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("updateStatusManually/{id}")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @PutMapping("updateStatusAutomatically/{id}")
    public Order updateOrderStatus(@PathVariable Long id) {
        return orderService.updateOrderStatus(id);
    }

    @PutMapping("payOrder/{id}")
    public Order payOrder(@PathVariable Long id) {
        return orderService.payOrder(id);
    }
}