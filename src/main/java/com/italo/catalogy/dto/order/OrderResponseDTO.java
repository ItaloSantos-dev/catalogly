package com.italo.catalogy.dto.order;

import com.italo.catalogy.dto.item.ItemResponseDTO;
import com.italo.catalogy.dto.order_item.OrderItemResponseDTO;
import com.italo.catalogy.model.enums.OrderStatus;
import com.italo.catalogy.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
        UUID id,
        String catalogName,
        OrderStatus orderStatus,
        UUID couponId,
        String couponSlug,
        BigDecimal couponDiscount,
        BigDecimal priceInitial,
        BigDecimal priceFinal,
        String paymentUrl,
        List<OrderItemResponseDTO> items

) {
}
