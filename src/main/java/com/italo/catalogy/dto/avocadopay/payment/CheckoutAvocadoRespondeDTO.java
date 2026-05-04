package com.italo.catalogy.dto.avocadopay.payment;

public record CheckoutAvocadoRespondeDTO(
        CheckoutData data,
        String error,
        Boolean success
) {
}
