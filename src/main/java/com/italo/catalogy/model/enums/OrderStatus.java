package com.italo.catalogy.model.enums;

public enum OrderStatus {
    CREATED,            // pedido criado
    PENDING_PAYMENT,    // aguardando pagamento
    PAID,               // pagamento confirmado
    PROCESSING,         // preparando (separando, embalando)
    COMPLETED,          // finalizado (entregue com sucesso)
    CANCELED;            // cancelado

    private String status;

    OrderStatus() {
        this.status = this.name();
    }
    public String getStatus() {
        return status;
    }
}
