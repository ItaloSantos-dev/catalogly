package com.italo.catalogy.dto.catalog;

public record UpdateCatalogRequestDTO(
        String name,
        String slug,
        String slogan,
        String about,
        String fisicAddress,
        String phone
) {
}
