package com.example.demo.service;

import com.example.demo.model.Branch;
import com.example.demo.model.Ticket;
import com.example.demo.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(String ticketId){
        return ticketRepository.findById(ticketId);
    }
    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void buyTicket(Ticket ticket) {
        // Add logic to handle ticket purchase
    }
}
