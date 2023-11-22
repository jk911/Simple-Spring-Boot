package com.example.demo.repositories;

import com.example.demo.model.Order;
import com.example.demo.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
public interface OrderRepository extends JpaRepository<Order, String> {

}
