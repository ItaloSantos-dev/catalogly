package com.italo.catalogy.dto.order;

import java.util.UUID;

public record ItemQuantity(
        UUID itemId,
        Integer quantity
) {
}
