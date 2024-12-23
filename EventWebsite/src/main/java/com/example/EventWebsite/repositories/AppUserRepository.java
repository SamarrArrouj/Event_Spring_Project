package com.example.EventWebsite.repositories;

import com.example.EventWebsite.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByEmail(String email);
    public AppUser findByUsername(String username);
}
