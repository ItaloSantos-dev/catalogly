package com.italo.catalogy.dto.order;

import com.italo.catalogy.dto.ItemQuantity;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequestDTO(
        UUID catalogId,
        @Nullable
        String couponSlug,
        List<ItemQuantity> items
) {
}
