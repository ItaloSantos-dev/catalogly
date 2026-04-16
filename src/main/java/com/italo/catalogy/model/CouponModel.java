package com.italo.catalogy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "tb_coupon",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"slug", "catalog_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CouponModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private CatalogModel catalog;

    @NotBlank
    @Column(name = "slug", nullable = false)
    private String slug;

    @DecimalMin("0.0001")
    @Digits(integer = 1, fraction = 4)
    @Column(name = "amount", nullable = false, precision = 5, scale = 4)
    private BigDecimal amount;

    @Digits(integer = 8, fraction = 2)
    @Column(name = "amount_minimum", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountMinimum;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "amount_discount_maximum", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountDiscountMaximum;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "coupon")
    private List<OrderModel> orders;



}
