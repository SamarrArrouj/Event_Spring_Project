package com.example.EventWebsite.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDto {
    @NotEmpty
    private String email;

    @Size(min = 6, message = "Minimum Password length is 6 characters")
    private String password;

    public @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty String email) {
        this.email = email;
    }

    public @Size(min = 6, message = "Minimum Password length is 6 characters") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 6, message = "Minimum Password length is 6 characters") String password) {
        this.password = password;
    }
}
