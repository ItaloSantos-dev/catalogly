package com.italo.catalogy.dto.nfe_io;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateServiceInvoiceRequestDTO(
        @NotBlank String externalId,
        Borrower borrower,
        @NotBlank String cityServiceCode,
        @NotBlank String federalServiceCode,
        @NotBlank String cnaeCode,
        @Nullable String nbsCode,
        @NotBlank String description,
        BigDecimal servicesAmount,
        @NotBlank String rpsSerialNumber,
        Instant issuedOn,
        Integer rpsNumber,
        @NotBlank String taxationType,
        BigDecimal issRate,
        BigDecimal issTaxAmount,
        BigDecimal deductionsAmount,
        BigDecimal discountUnconditionedAmount,
        BigDecimal discountConditionedAmount,
        BigDecimal irAmountWithheld,
        BigDecimal pisAmountWithheld,
        BigDecimal cofinsAmountWithheld,
        BigDecimal csllAmountWithheld,
        BigDecimal inssAmountWithheld,
        BigDecimal issAmountWithheld,
        BigDecimal othersAmountWithheld,
        ApproximateTax approximateTax,
        @Nullable String additionalInformation,
        Location location,
        ActivityEvent activityEvent
) {
}
