package com.italo.catalogy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.italo.catalogy.model.enums.StockOrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_stock_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockOrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerModel sellerModel;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierModel supplierModel;

    @NotNull
    @Column(name = "items_amount", nullable = false)
    private Integer itemsAmount;

    @NotNull
    @Column(name = "items_know", nullable = false)
    private Integer itemsKnow;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StockOrderStatus status;

    @DecimalMin("0.01")
    @Column(name = "price_estimated")
    private BigDecimal priceEstimated;

    @DecimalMin("0.01")
    @Column(name = "price_final" )
    private BigDecimal priceFinal;

    @Column(name = "invoice_xml_path")
    private String invoice_xml_path;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "stockOrder")
    private List<StockOrderItemModel> items;







}
