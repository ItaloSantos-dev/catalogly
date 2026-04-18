package com.italo.catalogy.dto.seller;

import com.italo.catalogy.model.enums.UserRole;

public record SellerResponseDTO(
        String firstName,
        String lastName,
        UserRole role,
        String phone
) {
}
