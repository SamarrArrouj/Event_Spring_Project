package com.example.EventWebsite.controllers;

import com.example.EventWebsite.models.Event;
import com.example.EventWebsite.models.Participation;
import com.example.EventWebsite.repositories.EventsRepository;
import com.example.EventWebsite.repositories.ParticipationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final EventsRepository eventsRepository;
    private final ParticipationRepository participationRepository;

    public DashboardController(EventsRepository eventsRepository, ParticipationRepository participationRepository) {
        this.eventsRepository = eventsRepository;
        this.participationRepository = participationRepository;
    }


    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Event> events = eventsRepository.findAll();
        int totalEvents = events.size();
        List<Event> latestEvents = events.stream()
                .sorted(Comparator.comparing(Event::getDate).reversed())
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("events", latestEvents);

        List<Participation> participations = participationRepository.findAll();
        int totalParticipants = participations.size();
        List<Participation> latestParticipants = participations.stream()
                .sorted(Comparator.comparing(Participation::getRegistrationDate).reversed())
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("participants", latestParticipants);

        model.addAttribute("totalEvents", totalEvents);
        model.addAttribute("totalParticipants", totalParticipants);


        return "dashboard";

    }
}