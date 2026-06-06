package com.italo.catalogy.dto.catalog;

import com.italo.catalogy.dto.catalog.dashboard.CatalogDashboard;
import com.italo.catalogy.dto.seller.SellerResponseDTO;

public record CatalogPrivateResponseDTO(
        String name,
        String slug,
        String slogan,
        String about,
        String fisicAddress,
        String phone,
        String imageIconUrl,
        String imageBannerUrl,
        SellerResponseDTO sellerData,
        CatalogDashboard catalogDashboard
        //Add outros itens
) {
}
