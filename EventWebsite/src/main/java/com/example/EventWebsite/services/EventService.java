package com.example.EventWebsite.services;

import com.example.EventWebsite.models.Event;
import com.example.EventWebsite.models.EventDto;
import com.example.EventWebsite.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventsRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Événement introuvable"));
    }

    public Page<EventDto> getUpcomingEvents(String search, Pageable pageable) {
        Page<Event> eventsPage;

        if (search != null && !search.isEmpty()) {
            eventsPage = eventRepository.searchEvents(search, pageable);
        } else {
            eventsPage = eventRepository.findAll(pageable);
        }

        return eventsPage.map(EventDto::fromEntity);
    }


}
