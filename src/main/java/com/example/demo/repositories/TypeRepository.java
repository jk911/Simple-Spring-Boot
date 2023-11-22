package com.example.demo.repositories;

import com.example.demo.model.Event;
import com.example.demo.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeRepository extends JpaRepository<TicketType, String> {
    @Query("SELECT u FROM TicketType u WHERE u.id = :id")
    Optional<TicketType> findByName(@Param("id") String id);

    @Query("SELECT u FROM TicketType u WHERE u.event = :event")
    List<TicketType> findByEvent(@Param("event") Event event);
}
