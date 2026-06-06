package com.italo.catalogy.dto.catalog.dashboard;

import com.italo.catalogy.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Resumo compacto de um pedido recente.
 */
public record RecentOrder(
        UUID orderId,
        String buyerName,
        OrderStatus status,
        BigDecimal total,
        LocalDateTime createdAt
) {
}

