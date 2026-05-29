package com.italo.catalogy.dto.invoice_xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InvoiceXmlDTO(
        NFe NFe
) {
}
