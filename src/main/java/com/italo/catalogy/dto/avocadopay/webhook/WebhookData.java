package com.italo.catalogy.dto.avocadopay.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.italo.catalogy.dto.avocadopay.Customer;
import com.italo.catalogy.dto.avocadopay.payment.CheckoutData;
@JsonIgnoreProperties(ignoreUnknown = true)
public record WebhookData(
        CheckoutData checkout,
        Customer customer,
        PayerInformation payerInformation,
        String reason
) {
}
