package com.italo.catalogy.dto.item_by_invoice;

import java.math.BigDecimal;

public record ItemByInvoice(
        String itemName,
        String code,
        BigDecimal price
) {


}
