package com.italo.catalogy.dto.invoice_xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Prod(
    String cProd,
    String xProd,
    BigDecimal vUnCom, //Preço unit
    BigDecimal vProd //Preço total
) {
}
