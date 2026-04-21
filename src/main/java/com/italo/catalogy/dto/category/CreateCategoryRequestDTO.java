package com.italo.catalogy.dto.category;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public record CreateCategoryRequestDTO (
        String name,
        String description,
        @Nullable
        List<UUID> items
){

}
