package com.example.demo.service;

import com.example.demo.model.Branch;
import com.example.demo.model.Event;
import com.example.demo.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(String eventId){
        return eventRepository.findById(eventId);
    }
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }
    public void deleteEventById(String eventId){
        eventRepository.deleteById(eventId);
    }
    public Event getById(String eventId){
        return eventRepository.getById(eventId);
    }
}
