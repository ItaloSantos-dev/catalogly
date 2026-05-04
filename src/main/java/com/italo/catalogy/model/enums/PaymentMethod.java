package com.italo.catalogy.model.enums;

public enum PaymentMethod {
    PIX("PIX"),
    CARD("CARD");

    private String method;

    PaymentMethod(String method) {
        this.method = method;
    }
    public String getMethod() {
        return this.method;
    }
}
