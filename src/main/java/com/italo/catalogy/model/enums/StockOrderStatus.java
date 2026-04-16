package com.italo.catalogy.model.enums;

public enum StockOrderStatus {
    CONCLUED("CONCLUED"),
    PENDING("PENDING"),
    CANCELED("CANCELED");

    private String status;

    StockOrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
