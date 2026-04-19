package com.italo.catalogy.dto.catalog;

public record CreateCatalogRequestDTO(
        String name,
        String slug,
        String slogan,
        String about,
        String fisicAddress,
        String phone,
        String imageIconUrl,
        String imageBannerUrl
) {
}
