package com.italo.catalogy.model;

import com.italo.catalogy.model.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
//Relacionar com o OrderItemModel,
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private UserModel buyer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerModel seller;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "catalog_id", nullable = false)
    private CatalogModel catalog;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponModel coupon;

    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "price_initial", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceInitial;


    @Digits(integer = 1, fraction = 4)
    @Column(name = "coupon_discount", precision = 5, scale = 4)
    private BigDecimal couponDiscount;

    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "price_final", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order")
    private List<PaymentModel> payment;

    @OneToOne(mappedBy = "order")
    private InvoiceModel invoice;

    @OneToMany(mappedBy = "order")
    private List<OrderItemModel> itens;
}
