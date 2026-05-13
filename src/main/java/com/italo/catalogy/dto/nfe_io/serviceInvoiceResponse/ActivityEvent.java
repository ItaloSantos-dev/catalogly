package com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

public record ActivityEvent(
        @NotBlank String name,
        Instant beginOn,
        Instant endOn,
        @NotBlank String code
) {
}