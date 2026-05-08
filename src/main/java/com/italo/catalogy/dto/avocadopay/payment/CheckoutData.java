package com.italo.catalogy.dto.avocadopay.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.italo.catalogy.model.enums.PaymentMethod;
import com.italo.catalogy.model.enums.PaymentStatus;
import java.time.Instant;
import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public record CheckoutData(
        String id,
        String externalId,
        String url,
        Integer amount,
        Integer paidAmount,
        List<ItemQuantityAvocadoPay> items,
        PaymentStatus status,
        List<String> coupons,
        List<PaymentMethod> methods,
        String frequency,
        Integer platformFee,
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
