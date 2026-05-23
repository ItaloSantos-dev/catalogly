package com.italo.catalogy.dto.stock_order;

import com.italo.catalogy.dto.ItemQuantity;

import java.util.List;
import java.util.UUID;

public record CreateStockOrderRequestDTO(
        UUID supplierId,
        List<ItemQuantity> items
) {
}
