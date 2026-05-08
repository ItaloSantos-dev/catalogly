package com.italo.catalogy.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO (
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String cpf
) {
}
