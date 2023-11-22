package com.example.demo.service;

import com.example.demo.model.Event;
import com.example.demo.model.TicketType;
import com.example.demo.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<TicketType> getAllTypes() {
        return typeRepository.findAll();
    }

    public Optional<TicketType> getTypeById(String typeId){
        return typeRepository.findById(typeId);
    }
    public TicketType addType(TicketType type) {
        return typeRepository.save(type);
    }
    public void deleteTypeById(String typeId){
        typeRepository.deleteById(typeId);
    }
    public Optional<TicketType> findByName(String id){
        return typeRepository.findByName(id);
    }
    public List<TicketType> getByEventId(Event event){
        return typeRepository.findByEvent(event);
    }
}
