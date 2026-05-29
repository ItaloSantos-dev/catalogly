package com.italo.catalogy.dto.invoice_xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Det(
    Prod prod
) {
}
