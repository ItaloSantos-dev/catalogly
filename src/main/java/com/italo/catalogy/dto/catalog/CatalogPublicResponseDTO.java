package com.italo.catalogy.dto.catalog;

public record CatalogPublicResponseDTO(
        String sellerName,
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
