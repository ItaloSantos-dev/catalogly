package com.italo.catalogy.dto.nfe_io.createServiceInvoice;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record ApproximateTaxCreate(
        @NotBlank String source,
        @NotBlank String version,
        BigDecimal totalRate
) {
}
