package com.italo.catalogy.dto.avocadopay.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CardDetails(
        String number,
        String brand
) {
}
