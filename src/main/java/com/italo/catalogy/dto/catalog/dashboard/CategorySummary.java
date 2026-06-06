package com.italo.catalogy.dto.catalog.dashboard;

/**
 * Resumo por categoria com contagem de itens.
 */
public record CategorySummary(
        String name,
        Integer itemsCount
) {
}

