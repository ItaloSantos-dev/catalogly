package com.italo.catalogy.dto.avocadopay.payment;

import com.italo.catalogy.model.enums.PaymentStatus;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record CheckoutData(
        String id,
        String externalId,
        String url,
        Integer amount,
        Integer paidAmount,
        List<ItemQuantityAvocadoPay> items,
        PaymentStatus status,
        List<String> coupons,
        String frequency,
        Boolean devMode,
        String customerId,
        String returnUrl,
        String completionUrl,
        String receiptUrl,
        Map<String, String > metadata,
        Instant createdAt,
        Instant updatedAt,
        String error

) {
}
