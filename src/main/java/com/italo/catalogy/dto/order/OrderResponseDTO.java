package com.italo.catalogy.dto.order;

import com.italo.catalogy.model.enums.OrderStatus;
import com.italo.catalogy.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponseDTO(
        UUID id,
        OrderStatus orderStatus,
        UUID couponId,
        String couponSlug,
        BigDecimal couponDiscount,
        BigDecimal priceInitial,
        BigDecimal priceFinal

) {
}
