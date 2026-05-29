package com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod;

import java.math.BigDecimal;
import java.util.UUID;

public record TieItemInvoice (
        String  itemInvoiceCprod,
        String itemInvoiceName,
        BigDecimal itemInvoicePrice
) {
}
