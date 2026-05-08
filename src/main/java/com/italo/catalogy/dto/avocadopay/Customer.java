package com.italo.catalogy.dto.avocadopay;

public record Customer(
        String id,
        String name,
        String email,
        String taxId
) {
}
