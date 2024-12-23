package com.example.EventWebsite.repositories;

import com.example.EventWebsite.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EventsRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.location LIKE %:search% OR e.title LIKE %:search% ")
    Page<Event> searchEvents(@Param("search") String search, Pageable pageable);


    Page<Event> findAll(Pageable pageable);
    }