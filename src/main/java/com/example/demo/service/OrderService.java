package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public void payOrder(Order order) {
        // Add logic to handle payment for the order
    }

    public Optional<Order> getOrderById(String customerId) {
        return orderRepository.findById(customerId);
    }

}
