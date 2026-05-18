package com.italo.catalogy.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public enum InvoiceStatus {
    CREATED("CREATED"),
    CANCELLED("CANCELLED"),
    NONE("NONE"),
    INSSUED("INSSUED"),
    DRAFT("DRAFT"),
    ERROR("ERROR");


    private String status;

    private static Map<String, InvoiceStatus> mappingInvoiceStatus = Map.of(
            "Error", ERROR,
            "None", NONE,
            "Created", CREATED,
            "Inssued", INSSUED,
            "Cancelled", CANCELLED,
            "Draft", DRAFT
    );
    InvoiceStatus (String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    @JsonCreator
    static   InvoiceStatus fromString(String value){
        return mappingInvoiceStatus.get(value);
    }
}
