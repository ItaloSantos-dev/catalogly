package com.italo.catalogy.dto.item;

import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateItemRequestDTO (
        @Nullable
        UUID categoryId,
        String name,
        String about,
        BigDecimal price,
        Integer stock
){
}
