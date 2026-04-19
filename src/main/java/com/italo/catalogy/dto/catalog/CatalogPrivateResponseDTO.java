package com.italo.catalogy.dto.catalog;

public record CatalogPrivateResponseDTO(
        String name,
        String slug,
        String slogan,
        String about,
        String fisicAddress,
        String phone,
        String imageIconUrl,
        String imageBannerUrl
        //Add outros itens
) {
}
