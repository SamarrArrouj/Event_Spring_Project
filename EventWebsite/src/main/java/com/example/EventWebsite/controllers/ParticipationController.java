package com.example.EventWebsite.controllers;

import com.example.EventWebsite.models.Participation;
import com.example.EventWebsite.models.Event;
import com.example.EventWebsite.models.ParticipationDto;
import com.example.EventWebsite.repositories.ParticipationRepository;
import com.example.EventWebsite.services.EventService;
import com.example.EventWebsite.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipationRepository participationRepository;

    @GetMapping("/{id}/register")
    public String showRegistrationForm(@PathVariable("id") Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        model.addAttribute("participationDto", new ParticipationDto());
        return "events/Registration";
    }

    @PostMapping("/{id}/register")
    public String registerForEvent(
            @PathVariable("id") Long eventId,
            @ModelAttribute("participationDto") @Valid ParticipationDto participationDto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            Event event = eventService.getEventById(eventId);
            model.addAttribute("event", event);
            return "events/Registration";
        }

        if (participationService.isParticipantAlreadyRegistered(eventId, participationDto.getEmail())) {
            Event event = eventService.getEventById(eventId);
            model.addAttribute("event", event);
            model.addAttribute("errorMessage", "You have already registered for this event.");
            return "events/Registration";
        }

        Participation participation = new Participation();
        Event event = eventService.getEventById(eventId);

        participation.setName(participationDto.getName());
        participation.setEmail(participationDto.getEmail());
        participation.setPhone(participationDto.getPhone());
        participation.setPaymentMethod(participationDto.getPaymentMethod());
        participation.setRegistrationDate(new Date());
        participation.setEvent(event);

        participationService.saveParticipation(participation);

        model.addAttribute("event", event);

        return "events/SuccessRegistration";
    }

    @GetMapping("/ParticipantList")
    public String listParticipants(@RequestParam(value = "search",required = false)String search,Model model) {
       var participants = participationRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("participation", participants);
        return "events/ParticipantList";
    }

    @GetMapping("/deleteParticipant")
    public String deleteParticipant(@RequestParam Long id) {
        try {

            Participation participation = participationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Participant not found"));

            participationRepository.delete(participation);


            return "redirect:/events/ParticipantList";
        } catch (Exception ex) {

            System.out.println("Exception: " + ex.getMessage());

            return "error";
        }
    }


}
