package com.italo.catalogy.dto.item;

import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemResponseDTO (
        UUID id,
        String categoryName,
        String name,
        String about,
        BigDecimal price,
        Integer stock,
        String imagePath1,
        String imagePath2,
        String imagePath3

){
}
