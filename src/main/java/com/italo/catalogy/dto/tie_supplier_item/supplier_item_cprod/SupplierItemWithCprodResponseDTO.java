package com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

public record SupplierItemWithCprodResponseDTO(
        List<TieItemStockOrder> stockOrderItems,
        List<TieItemInvoice> invoiceItems,
        String xmlLink,
        UUID stockOrderId,
        UUID supplierId
) {
}
