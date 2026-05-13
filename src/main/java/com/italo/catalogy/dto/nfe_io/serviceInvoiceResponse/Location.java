package com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;

public record Location(
        @NotBlank String state,
        @NotBlank String country,
        @NotBlank String postalCode,
        @NotBlank String street,
        @NotBlank String number,
        @NotBlank String district,
        @Nullable String additionalInformation,
        City city
) {
}