package com.example.EventWebsite.services;

import com.example.EventWebsite.models.Event;
import com.example.EventWebsite.models.Participation;
import com.example.EventWebsite.models.ParticipationDto;
import com.example.EventWebsite.repositories.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipationService {
    @Autowired
    private ParticipationRepository participationRepository;

    public Participation createParticipation(ParticipationDto participationDto, Event event) {
        Participation participation = new Participation();
        participation.setName(participationDto.getName());
        participation.setEmail(participationDto.getEmail());
        participation.setPhone(participationDto.getPhone());
        participation.setPaymentMethod(participationDto.getPaymentMethod());
        participation.setRegistrationDate(new Date());
        participation.setEvent(event);
        return participationRepository.save(participation);
    }


    public List<Participation> findAllParticipants() {
        return participationRepository.findAll();
    }

    public void saveParticipation(Participation participation) {
        participationRepository.save(participation);
    }

    public boolean isParticipantAlreadyRegistered(Long eventId, String email) {
        return participationRepository.existsByEventIdAndEmail(eventId, email);
    }

    public List<Participation> findAll() {
        return participationRepository.findAll();
    }



    }


