package com.pouya.javatest.FormRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "form.email.required")
    @Email(message = "form.email.format")
    private String email;

    @NotBlank(message = "form.password.required")
    @Min(value = 2 , message = "form.password.min")
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}