package com.italo.catalogy.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @Email
        @NotBlank
        String email,

        @Email
        @NotBlank
        String password
) {
}
