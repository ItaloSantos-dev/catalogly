package com.italo.catalogy.dto.supplier_item;

import com.italo.catalogy.dto.item.ItemResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record SupplierItemResponseDTO(
        UUID id,
        String supplierName,
        ItemResponseDTO item,
        String cProd,
        BigDecimal lastPrice

) {
}
