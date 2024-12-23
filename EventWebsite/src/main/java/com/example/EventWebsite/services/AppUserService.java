package com.example.EventWebsite.services;

import com.example.EventWebsite.models.AppUser;
import com.example.EventWebsite.models.Role;
import com.example.EventWebsite.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new UsernameNotFoundException("Utilisateur avec l'email " + email + " non trouvé.");
        }

        Role role = appUser.getRole();

        return User.withUsername(appUser.getEmail())
                .password(appUser.getPassword())
                .authorities("ROLE_" + role.name())
                .build();

    }

    public AppUser authenticate(String username, String password, String role) {
        AppUser user = appUserRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur avec le nom " + username + " non trouvé.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe incorrect.");
        }

        if (!user.getRole().name().equalsIgnoreCase(role)) {
            throw new IllegalArgumentException("Rôle invalide. L'utilisateur n'a pas le rôle " + role);
        }

        return user;
    }
}
