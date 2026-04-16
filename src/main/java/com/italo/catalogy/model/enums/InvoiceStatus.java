package com.italo.catalogy.model.enums;

public enum InvoiceStatus {
    DRAFT("DRAFT"),
    SENDING("SENDING"),
    AUTHORIZED("AUTHORIZED"),
    REJECTED("REJECTED");

    private String status;

    InvoiceStatus (String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
