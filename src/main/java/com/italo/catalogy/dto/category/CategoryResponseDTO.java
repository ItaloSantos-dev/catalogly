package com.italo.catalogy.dto.category;

import java.util.List;
import java.util.UUID;

import com.italo.catalogy.dto.item.ItemResponseDTO;

public record CategoryResponseDTO(
        UUID id,
        String name,
        String description,
        //Lista de itens
        Integer itemsCount,
        List<ItemResponseDTO> items
) {
}
