package com.italo.catalogy.dto.tie_supplier_item.update_cprod;

import java.util.List;

public record UpdateCprodOfSupplierItemsRequestDTO(
    List<TieCprodInvoiceWithSupplierItemId> tieItems
) {
}
