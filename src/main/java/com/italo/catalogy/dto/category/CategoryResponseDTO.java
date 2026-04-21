package com.italo.catalogy.dto.category;

public record CategoryResponseDTO(
        String name,
        String description,
        //Lista de itens
        Integer itemsCount
) {
}
