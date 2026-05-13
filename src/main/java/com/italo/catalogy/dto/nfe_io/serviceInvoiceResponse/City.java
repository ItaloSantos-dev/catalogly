package com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse;

import jakarta.validation.constraints.NotBlank;

public record City(
        @NotBlank String code,
        @NotBlank String name
) {
}