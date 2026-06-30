package com.italo.catalogy.dto.item;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UpdateItemRequestDTO (
        UUID categoryId,
        String name,
        String about,
        BigDecimal price,
        Integer stock,
        List<Boolean> updateImages
){
}
