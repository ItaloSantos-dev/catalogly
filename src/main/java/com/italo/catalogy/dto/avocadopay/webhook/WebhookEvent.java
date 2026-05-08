package com.italo.catalogy.dto.avocadopay.webhook;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public enum WebhookEvent {
    CHECKOUT_COMPLETED("CHECKOUT_COMPLETED"),
    CHECKOUT_DISPUTED("CHECKOUT_DISPUTED"),
    CHECKOUT_REFUNDED("CHECKOUT_REFUNDED");

    static final Map<String, WebhookEvent> mappingWebhokEvent = Map.of(
            "checkout.completed", CHECKOUT_COMPLETED,
            "checkout.disputed", CHECKOUT_DISPUTED,
            "checkout.refunded", CHECKOUT_REFUNDED
    );

    private String webhookEvent;

    WebhookEvent (String webhookEvent){
        this.webhookEvent=webhookEvent;
    }

    public String getWebhookEvent() {
        return webhookEvent;
    }

    @JsonCreator
    public static WebhookEvent fromString(String value){
        return mappingWebhokEvent.get(value);
    }
}
