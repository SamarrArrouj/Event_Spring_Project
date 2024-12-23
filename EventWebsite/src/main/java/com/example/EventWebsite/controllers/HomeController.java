package com.example.EventWebsite.controllers;

import com.example.EventWebsite.models.Event;
import com.example.EventWebsite.repositories.EventsRepository;
import com.example.EventWebsite.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventsRepository eventsRepository;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Event> events = eventsRepository.findAll();
        List<Event> latestEvents = events.stream()
                .sorted(Comparator.comparing(Event::getDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
        model.addAttribute("events", latestEvents);
        return "Home";
    }
    @GetMapping("/UpcomingEvent")
    public String showUpcomingEvent(Model model) {
        List<Event> events = eventsRepository.findAll();
        List<Event> latestEvents = events.stream()
                .sorted(Comparator.comparing(Event::getDate).reversed())
                .collect(Collectors.toList());
        model.addAttribute("events", latestEvents);
        return "UpcomingEvent";
    }
}
