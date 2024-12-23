package com.example.EventWebsite.controllers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(java.nio.file.AccessDeniedException.class)
    public String handleAccessDeniedException(java.nio.file.AccessDeniedException ex, Model model) {
        model.addAttribute("error", "Access Denied");
        model.addAttribute("message", ex.getMessage());
        return "error/403";
    }
}
