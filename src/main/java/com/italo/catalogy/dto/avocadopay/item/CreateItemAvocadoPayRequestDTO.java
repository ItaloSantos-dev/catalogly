package com.italo.catalogy.dto.avocadopay.item;

import java.util.UUID;

public record CreateItemAvocadoPayRequestDTO(
        UUID externalId,
        String name,
        Integer price,
        String currency
) {
}
