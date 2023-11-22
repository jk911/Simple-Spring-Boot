package com.example.demo.repositories;

import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface TicketRepository extends JpaRepository<Ticket, String> {

}
