package com.italo.catalogy.dto.catalog;

import java.util.List;
import java.util.UUID;

import com.italo.catalogy.dto.catalog.dashboard.CatalogDashboard;
import com.italo.catalogy.dto.category.CategoryNameAndId;
import com.italo.catalogy.dto.item.ItemResponseDTO;
import com.italo.catalogy.dto.seller.SellerResponseDTO;

public record CatalogPrivateResponseDTO(
        UUID id,
        String name,
        String slug,
        String slogan,
        String about,
        String fisicAddress,
        String phone,
        String imageIconUrl,
        String imageBannerUrl,
        SellerResponseDTO sellerData,
        CatalogDashboard catalogDashboard,
        List<CategoryNameAndId> categoryNamesAndIds,
        List<ItemResponseDTO> items
        //Add outros itens
) {
}
