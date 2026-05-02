package com.italo.catalogy.dto.avocadopay.item;

import java.time.Instant;

public record ItemDataDTO(
        String externalId,
        String name,
        String description,
        Integer price,
        Boolean devMode,
        String currency,
        Instant createdAt,
        Instant updatedAt,
        String status,
        String id,
        String imageUrl,
        String cycle
) {
}
