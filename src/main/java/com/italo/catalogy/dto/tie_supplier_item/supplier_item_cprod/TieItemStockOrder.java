package com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod;

import java.util.UUID;

public record TieItemStockOrder(
        UUID stockOrderItemId,
        String stockOrderItemName,
        String linkImage1
) {
}
