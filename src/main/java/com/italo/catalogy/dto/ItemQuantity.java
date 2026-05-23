package com.italo.catalogy.dto;

import java.util.UUID;

public record ItemQuantity(
        UUID itemId,
        Integer quantity
) {
}
