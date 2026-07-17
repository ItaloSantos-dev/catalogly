package com.italo.catalogy.dto.order_item;

import java.math.BigDecimal;
import java.util.UUID;

import com.italo.catalogy.dto.item.ItemResponseDTO;



public record OrderItemResponseDTO(
    UUID id,
    BigDecimal priceUnique,
    Integer amount,
    BigDecimal priceFinal,
    ItemResponseDTO item
) {
    

}
