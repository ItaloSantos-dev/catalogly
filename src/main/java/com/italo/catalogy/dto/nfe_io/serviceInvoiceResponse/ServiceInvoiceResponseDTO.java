package com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public record ServiceInvoiceResponseDTO(
        @NotBlank String id,
        @NotBlank String externalId,
        @NotBlank String environment,
        @NotBlank String flowStatus,
        Provider provider,
        Borrower borrower,
        Integer batchNumber,
        Integer number,
        @NotBlank String status,
        @NotBlank String rpsType,
        @NotBlank String rpsStatus,
        @NotBlank String taxationType,
        Instant issuedOn,
        @NotBlank String rpsSerialNumber,
        Integer rpsNumber,
        @NotBlank String cityServiceCode,
        @NotBlank String federalServiceCode,
        @NotBlank String cnaeCode,
        @NotBlank String description,
        BigDecimal servicesAmount,
        BigDecimal paidAmount,
        @NotBlank String paymentMethod,
        BigDecimal deductionsAmount,
        BigDecimal discountUnconditionedAmount,
        BigDecimal discountConditionedAmount,
        BigDecimal baseTaxAmount,
        BigDecimal issRate,
        BigDecimal issTaxAmount,
        BigDecimal irAmountWithheld,
        BigDecimal pisAmountWithheld,
        BigDecimal cofinsAmountWithheld,
        BigDecimal csllAmountWithheld,
        BigDecimal inssAmountWithheld,
        @Nullable String nbsCode,
        BigDecimal issAmountWithheld,
        BigDecimal othersAmountWithheld,
        BigDecimal amountWithheld,
        BigDecimal amountNet,
        Location location,
        ActivityEvent activityEvent,
        ApproximateTax approximateTax,
        @Nullable String additionalInformation,
        Instant createdOn
) {
}