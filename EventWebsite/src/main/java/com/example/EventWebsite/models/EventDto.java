package com.example.EventWebsite.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;

public class EventDto {
    @NotEmpty(message = "The title is required")
    private String title;
    @Size(min = 10, message = "The description should be at least 10 characters")
    @Size(max = 2000, message = "The description cannot exceed 2000 characters")
    private String description;
    @NotEmpty(message = "The category is required")
    private String category;
    @NotEmpty(message = "The location is required")
    private String location;
    @Min(1)
    private Long availablePlaces;
    @Min(0)
    private double price;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private MultipartFile imageFileName;

    public @NotEmpty(message = "The title is required") String getTitle() {
        return title;
    }

    public @Size(min = 10, message = "The description should be at least 10 characters") @Size(max = 2000, message = "The description cannot exceed 2000 characters") String getDescription() {
        return description;
    }

    public @NotEmpty(message = "The category is required") String getCategory() {
        return category;
    }

    public @NotEmpty(message = "The location is required") String getLocation() {
        return location;
    }

    public @Min(1) Long getAvailablePlaces() {
        return availablePlaces;
    }

    @Min(0)
    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public MultipartFile getImageFileName() {
        return imageFileName;
    }

    public void setTitle(@NotEmpty(message = "The title is required") String title) {
        this.title = title;
    }

    public void setDescription(@Size(min = 10, message = "The description should be at least 10 characters") @Size(max = 2000, message = "The description cannot exceed 2000 characters") String description) {
        this.description = description;
    }

    public void setCategory(@NotEmpty(message = "The category is required") String category) {
        this.category = category;
    }

    public void setLocation(@NotEmpty(message = "The location is required") String location) {
        this.location = location;
    }

    public void setAvailablePlaces(@Min(1) Long availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    public void setPrice(@Min(0) double price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setImageFileName(MultipartFile imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Event toEntity() {
        Event event = new Event();
        event.setTitle(this.title);
        event.setDescription(this.description);
        event.setCategory(this.category);
        event.setLocation(this.location);
        event.setPrice(this.price);
        event.setAvailablePlaces(this.availablePlaces);
        event.setDate(this.date);
        return event;
    }



    public static EventDto fromEntity(Event event) {
        if (event == null) {
            return null;
        }

        EventDto dto = new EventDto();
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setCategory(event.getCategory());
        dto.setLocation(event.getLocation());
        dto.setPrice(event.getPrice());
        dto.setAvailablePlaces(event.getAvailablePlaces());
        dto.setDate(event.getDate());
        return dto;
    }
}

