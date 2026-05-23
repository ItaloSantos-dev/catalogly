package com.italo.catalogy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_stock_order_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockOrderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "stock_order_id", nullable = false)
    private StockOrderModel stockOrder;

    @ManyToOne
    @JoinColumn(name = "supplier_item_id", nullable = false)
    private SupplierItemModel supplierItem;

    @Column(name="amount",nullable = false)
    private Integer amount;

    @Column(name="priceUnique")
    private BigDecimal priceUnique;

    @Column(name="priceFinal")
    private BigDecimal priceFinal;
}
