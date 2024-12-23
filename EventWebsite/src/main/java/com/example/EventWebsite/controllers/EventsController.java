package com.example.EventWebsite.controllers;

import com.example.EventWebsite.models.Event;
import com.example.EventWebsite.models.EventDto;
import com.example.EventWebsite.repositories.EventsRepository;
import com.example.EventWebsite.repositories.ParticipationRepository;
import com.example.EventWebsite.services.EventService;
import com.example.EventWebsite.services.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsRepository eventsRep;
    @Autowired
    private EventService eventService;
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private ParticipationService participationService;

    @GetMapping({"", "/"})
    public String showEventList(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Event> events = eventsRep.findAll(pageable);
        model.addAttribute("events", events.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", events.getTotalPages());
        return "events/index";
    }


    @GetMapping("/create")
    public String showCreatePage(Model model) {
        EventDto eventDto = new EventDto();
        model.addAttribute("eventDto", eventDto);
        return "events/CreateEvent";
    }

    @PostMapping("/create")
    public String createEvent(
            @Valid @ModelAttribute EventDto eventDto,
            BindingResult result
    ) {

        if (eventDto.getImageFileName().isEmpty()) {
            result.addError(new FieldError("eventDto", "imageFile", "Image field is required"));
        }

        if (result.hasErrors()) {
            return "events/CreateEvent";
        }

        MultipartFile image = eventDto.getImageFileName();
        Date date = new Date();
        String storageFileName = date.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            Event event = new Event();
            event.setTitle(eventDto.getTitle());
            event.setDescription(eventDto.getDescription());
            event.setLocation(eventDto.getLocation());
            event.setCategory(eventDto.getCategory());
            event.setAvailablePlaces(eventDto.getAvailablePlaces());
            event.setPrice(eventDto.getPrice());
            event.setDate(date);
            event.setImageFileName(storageFileName);
            eventsRep.save(event);

            return "redirect:/events";

        } catch (Exception ex) {
            ex.printStackTrace();
            result.addError(new FieldError("eventDto", "imageFile", "An error occurred while uploading the file."));
            return "events/CreateEvent";
        }
    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam Long id) {


        try {
            Event event = eventsRep.findById(id).get();
            model.addAttribute("event", event);

            EventDto eventDto = new EventDto();
            eventDto.setTitle(event.getTitle());
            eventDto.setDescription(event.getDescription());
            eventDto.setCategory(event.getCategory());
            eventDto.setLocation(event.getLocation());
            eventDto.setAvailablePlaces(event.getAvailablePlaces());
            eventDto.setPrice(event.getPrice());
            eventDto.setDate(event.getDate());
            model.addAttribute("eventDto", eventDto);


        } catch (Exception ex) {
            System.out.println("Exception:" + ex.getMessage());
            return "redirect:/events";
        }
        return "events/EditEvent";
    }

    @PostMapping("/edit")
    public String updateEvent(Model model, @RequestParam Long id, @Valid @ModelAttribute EventDto eventDto, BindingResult result) {
        try {
            Event event = eventsRep.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
            model.addAttribute("event", event);

            if (result.hasErrors()) {
                return "events/EditEvent";
            }

            if (!eventDto.getImageFileName().isEmpty()) {
                // Supprimer l'ancienne image
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + event.getImageFileName());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Error while deleting old image: " + ex.getMessage());
                }

                // Sauvegarder la nouvelle image
                MultipartFile image = eventDto.getImageFileName();
                String storageFileName = new Date().getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                event.setImageFileName(storageFileName);
            }

            event.setTitle(eventDto.getTitle());
            event.setDescription(eventDto.getDescription());
            event.setLocation(eventDto.getLocation());
            event.setCategory(eventDto.getCategory());
            event.setAvailablePlaces(eventDto.getAvailablePlaces());
            event.setPrice(eventDto.getPrice());
            event.setDate(eventDto.getDate());
            eventsRep.save(event);

        } catch (Exception ex) {
            System.out.println("Exception:" + ex.getMessage());
        }
        return "redirect:/events/";
    }

    @GetMapping("/delete")
    public String deleteEvent(
            @RequestParam Long id) {


        try {
            Event event = eventsRep.findById(id).get();


            Path imagePath = Paths.get("public/images/" + event.getImageFileName());

            try {
                Files.delete(imagePath);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
            eventsRep.delete(event);
        } catch (Exception ex) {
            System.out.println("Exception:" + ex.getMessage());
        }

        return "redirect:/events/";
    }


    @GetMapping("/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "events/ViewDetails";
    }

    @GetMapping("/upcoming")
    public String showUpcomingEvents(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        System.out.println("Search term: " + search);

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "date"));
        Page<EventDto> events = eventService.getUpcomingEvents(search, pageable);

        model.addAttribute("events", events.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", events.getTotalPages());
        model.addAttribute("search", search);

        return "/UpcomingEvent";
    }
}

