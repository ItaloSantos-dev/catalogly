package com.italo.catalogy.model.enums;

public enum ContactSupplierType {
    EMAIL("SITE"),
    PHONE("PHONE");

    private final String value;

    ContactSupplierType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
