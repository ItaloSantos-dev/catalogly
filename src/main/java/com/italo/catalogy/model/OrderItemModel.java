package com.italo.catalogy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_order_item")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemModel item;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "price_unique", nullable = false, precision = 10, scale=2)
    private BigDecimal priceUnique;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "price_final", nullable = false, precision = 10, scale=2)
    private BigDecimal priceFinal;

}
