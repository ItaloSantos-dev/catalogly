package com.italo.catalogy.dto.nfe_io;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;

public record Address(
        @NotBlank String country,
        @NotBlank String postalCode,
        @NotBlank String street,
        @NotBlank String number,
        @Nullable String additionalInformation,
        @NotBlank String district,
        City city,
        @NotBlank String state
) {
}
