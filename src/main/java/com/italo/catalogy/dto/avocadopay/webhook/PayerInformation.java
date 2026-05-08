package com.italo.catalogy.dto.avocadopay.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.italo.catalogy.dto.avocadopay.payment.Card;
import com.italo.catalogy.model.enums.PaymentMethod;
@JsonIgnoreProperties(ignoreUnknown = true)
public record PayerInformation(
        PaymentMethod method,
        CardDetails CARD
) {
}
