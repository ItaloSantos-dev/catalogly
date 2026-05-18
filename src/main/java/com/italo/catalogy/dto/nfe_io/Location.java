package com.italo.catalogy.dto.nfe_io;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;
import com.italo.catalogy.dto.nfe_io.City;

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
