package com.example.EventWebsite.repositories;

import com.example.EventWebsite.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    boolean existsByEventIdAndEmail(Long eventId, String email);


}
