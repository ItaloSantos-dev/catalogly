package com.italo.catalogy.dto.tie_supplier_item.update_cprod;

import java.util.UUID;

public record TieCprodInvoiceWithSupplierItemId(
        String cProd,
        //Id do stockOrderItem
        UUID stockOrderItemId
) {
}
