package com.italo.catalogy.dto.stock_order;

import com.italo.catalogy.dto.stock_order_item.StockOrderItemResponseDTO;
import com.italo.catalogy.model.enums.StockOrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record StockOrderResponseDTO(
        UUID id,
        UUID sellerId,
        String sellerName,
        UUID supplierId,
        String supplierName,
        //Itens totais, item*amount
        Integer itemsAmount,
        StockOrderStatus status,
        BigDecimal priceEstimated,
        BigDecimal priceFinal,
        String invoiceXmlurl,
        List<StockOrderItemResponseDTO> supplierItems
) {
}
