package com.italo.catalogy.dto.coupon;

import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

public record UpdateCouponRequestDTO(
        String slug,
        BigDecimal amount,
        @Nullable
        BigDecimal amountMinimum,
        @Nullable
        BigDecimal amountDiscountMaximum
) {
}
