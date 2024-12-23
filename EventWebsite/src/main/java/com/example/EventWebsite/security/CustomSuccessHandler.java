package com.example.EventWebsite.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        System.out.println("Authentication successful for user: " + username + " with role: " + role);


        if ("ROLE_admin".equals(role)) {
            response.sendRedirect("/dashboard");
        } else if ("ROLE_participant".equals(role)) {
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/home");
        }
    }
}

