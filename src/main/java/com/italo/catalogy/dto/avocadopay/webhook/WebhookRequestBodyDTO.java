package com.italo.catalogy.dto.avocadopay.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WebhookRequestBodyDTO(
    WebhookEvent event,
    Integer apiVersion,
    Boolean devMode,
    WebhookData data


) {
}
