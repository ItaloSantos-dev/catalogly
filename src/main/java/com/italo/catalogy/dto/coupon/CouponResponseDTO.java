package com.italo.catalogy.dto.coupon;


import java.math.BigDecimal;
import java.util.UUID;

public record CouponResponseDTO(
        UUID id,
        String slug,
        BigDecimal amount,
        BigDecimal amountMinimum,
        BigDecimal amountDiscountMaximum
) {
}
