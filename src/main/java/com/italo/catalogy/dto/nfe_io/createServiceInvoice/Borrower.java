package com.italo.catalogy.dto.nfe_io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;

public record Borrower(
        @NotBlank String type,
        @NotBlank String name,
        String federalTaxNumber,
        @Nullable String municipalTaxNumber,
        @NotBlank String taxRegime,
        @NotBlank String phoneNumber,
        @Email @NotBlank String email,
        Address address
) {
}
