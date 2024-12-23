package com.example.EventWebsite.models;


import jakarta.validation.constraints.*;

import java.util.Date;

public class ParticipationDto {

    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom doit avoir entre 2 et 100 caractères")
    private String name;

    @NotBlank(message = "L'email ne peut pas être vide")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
    private String phone;


    @NotBlank(message = "La méthode de paiement ne peut pas être vide")
    private String paymentMethod;

    private Date registrationDate;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public static ParticipationDto fromEntity(Participation participation) {
        if (participation == null) {
            return null;
        }

        ParticipationDto dto = new ParticipationDto();
        dto.setId(participation.getId());
        dto.setName(participation.getName());
        dto.setEmail(participation.getEmail());
        dto.setPhone(participation.getPhone());
        dto.setPaymentMethod(participation.getPaymentMethod());
        dto.setRegistrationDate(participation.getRegistrationDate());
        return dto;

    }


    public Participation toEntity() {
        Participation participation = new Participation();
        participation.setId(this.id);
        participation.setName(this.name);
        participation.setPhone(this.phone);
        return participation;

    }
}

