package com.italo.catalogy.dto.catalog;

import java.util.List;
import java.util.UUID;
import java.util.Locale.Category;

import com.italo.catalogy.dto.category.CategoryResponseDTO;
import com.italo.catalogy.dto.coupon.CouponResponseDTO;
import com.italo.catalogy.dto.item.ItemResponseDTO;

public record CatalogPublicResponseDTO(
        UUID id,
        String sellerName,
        String name,
        String slug,
        String slogan,
        String about,
        String fisicAddress,
        String phone,
        String imageIconUrl,
        String imageBannerUrl,
        List<ItemResponseDTO> items,
        List<CategoryResponseDTO> categorys,
        List<CouponResponseDTO> coupons
        //Add outros itens

) {
}
