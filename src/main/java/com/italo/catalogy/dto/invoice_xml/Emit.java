package com.italo.catalogy.dto.invoice_xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Emit(
        @JacksonXmlProperty(localName = "CNPJ")
        String cnpj,
        @JacksonXmlProperty(localName = "xNome")
        String name
) {
}
