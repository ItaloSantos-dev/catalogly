package com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse;

import com.italo.catalogy.dto.nfe_io.Address;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public record Provider(
        @NotBlank String tradeName,
        @NotBlank String taxRegime,
        @NotBlank String specialTaxRegime,
        @NotBlank String legalNature,
        Long regionalTaxNumber,
        BigDecimal issRate,
        @NotBlank String federalTaxDetermination,
        @NotBlank String municipalTaxDetermination,
        @Nullable String parentId,
        @NotBlank String id,
        @NotBlank String name,
        Long federalTaxNumber,
        Address address,
        @NotBlank String status,
        @NotBlank String type,
        Instant createdOn,
        Instant modifiedOn
) {
}