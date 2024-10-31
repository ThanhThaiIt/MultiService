package com.example.multiservice.dto.request;


import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
        @NotNull
        String email,
        @NotNull
        String password) {
}
