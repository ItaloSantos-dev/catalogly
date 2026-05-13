package com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record ApproximateTax(
        @NotBlank String source,
        @NotBlank String version,
        BigDecimal totalRate,
        BigDecimal totalAmount
) {
}