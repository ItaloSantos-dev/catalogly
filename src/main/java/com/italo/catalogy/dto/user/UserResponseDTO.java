package com.italo.catalogy.dto.user;

import com.italo.catalogy.model.enums.UserRole;

public record UserResponseDTO(
        String firstName,
        String lastName,
        UserRole role
) {
}
