package com.italo.catalogy.dto.category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String name,
        String description,
        //Lista de itens
        Integer itemsCount
) {
}
