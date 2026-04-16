package com.italo.catalogy.model.enums;

public enum PaymentStatus {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    APPROVED("APPROVED"),
    FAILED("FAILED"),
    CANCELED("CANCELED"),
    REFUNDED("REFUNDED");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
