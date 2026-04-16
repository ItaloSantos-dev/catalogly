package com.italo.catalogy.model.enums;

public enum ContactSuplierType {
    EMAIL("SITE"),
    PHONE("PHONE"),
    WHATSAPP("WHATSAPP");

    private final String value;

    ContactSuplierType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
