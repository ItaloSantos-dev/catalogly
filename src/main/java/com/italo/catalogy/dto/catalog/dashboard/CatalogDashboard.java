package com.italo.catalogy.dto.catalog.dashboard;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * DTO para representar os dados exibidos no dashboard de um catálogo.
 * Contém métricas agregadas (contagens, valores), além de pequenas listas
 * de resumo (top itens, pedidos recentes, categorias).
 *
 * Este arquivo foi pensado para atender empreendedores de porte médio/pequeno
 * que precisam de um panorama rápido do catálogo e do negócio.
 */
public record CatalogDashboard(
        UUID catalogId,
        String name,
        String slug,

        /* Contagens básicas */
        Integer totalItems,
        Integer totalCategories,
        Integer totalOrders,

        /* Situação de pedidos */
        Integer completedOrders,
        Integer pendingOrders,

        /* Financeiro */
        BigDecimal totalRevenue,
        BigDecimal averageOrderValue,

        /* Estoque */
        BigDecimal stockValue,
        Integer lowStockItems,
        Integer outOfStockItems,

        /* Promoções / cupons */
        Integer activeCoupons,

        /* Fiscal / pagamentos */
        Integer pendingInvoices,

        /* Listas de resumo úteis para exibição no dashboard */
        List<TopItem> topItems,
        List<RecentOrder> recentOrders,
        List<CategorySummary> categories

) {

}
