package com.italo.catalogy.dto.invoice_xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InfNFe(
    Emit emit,
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "det")
    List<Det> det
) {
}
