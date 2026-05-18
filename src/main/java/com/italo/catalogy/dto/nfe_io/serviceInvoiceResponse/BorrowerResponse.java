package com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse;

import com.italo.catalogy.dto.nfe_io.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record BorrowerResponse(
        @NotBlank String id,
        @NotBlank String name,
        Long federalTaxNumber,
        @Email @NotBlank String email,
        @NotBlank String phoneNumber,
        Address address,
        @NotBlank String status,
        @NotBlank String type,
        Instant createdOn
) {
}