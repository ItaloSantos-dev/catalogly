package com.italo.catalogy.dto.auth;

public record RegisterRequestDTO (
        String firstName,
        String lastName,
        String email,
        String password
) {
}
