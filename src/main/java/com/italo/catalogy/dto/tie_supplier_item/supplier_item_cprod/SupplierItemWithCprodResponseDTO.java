package com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod;

import java.util.List;

public record SupplierItemWithCprodResponseDTO(
        List<TieItemStockOrder> stockOrderItems,
        List<TieItemInvoice> invoiceItems,
        String xmlLink
) {
}
