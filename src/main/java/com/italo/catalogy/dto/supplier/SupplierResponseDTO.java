package com.italo.catalogy.dto.supplier;

import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;
import com.italo.catalogy.model.enums.ContactSupplierType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public record SupplierResponseDTO(
        UUID id,
        @NotBlank
        String name,
        @CNPJ
        String cnpj,
        @NotNull
        ContactSupplierType contactSupplierType,
        @NotBlank
        String contactValue,
        boolean active,
        @Nullable
        List<SupplierItemResponseDTO> supplierItems
) {
}
