package com.italo.catalogy.dto.tie_supplier_item.update_cprod;

import java.util.UUID;

public record TieCprodInvoiceWithSupplierItemId(
        String cProd,
        UUID stockOrderItemId
) {
}
