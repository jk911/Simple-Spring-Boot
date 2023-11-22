package com.example.demo.repositories;

import com.example.demo.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, String> {
    @Query("SELECT u FROM Event u WHERE u.id = :id")
    Event getById(@Param("id") String id);
}

