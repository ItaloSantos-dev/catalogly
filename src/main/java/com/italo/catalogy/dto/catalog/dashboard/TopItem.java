package com.italo.catalogy.dto.catalog.dashboard;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representa um item com maior volume de vendas (para o proprietário do catálogo).
 */
public record TopItem(
        UUID itemId,
        String name,
        Integer quantitySold,
        BigDecimal revenue
) {
}

