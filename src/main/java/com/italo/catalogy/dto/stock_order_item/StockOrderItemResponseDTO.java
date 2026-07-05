package com.italo.catalogy.dto.stock_order_item;

import java.math.BigDecimal;

import com.italo.catalogy.dto.item.ItemResponseDTO;
import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;

public record StockOrderItemResponseDTO(
        SupplierItemResponseDTO supplierItem,
        Integer amount,
        BigDecimal priceUnique,
        BigDecimal priceFinal
) {
}
