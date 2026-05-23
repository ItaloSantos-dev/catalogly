package com.italo.catalogy.dto.stock_order_item;

import java.math.BigDecimal;

public record StockOrderItemResponseDTO(
        Integer amount,
        BigDecimal priceUnique,
        BigDecimal priceFinal
) {
}
