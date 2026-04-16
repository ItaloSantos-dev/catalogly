package com.italo.catalogy.model;

import com.italo.catalogy.model.enums.PaymentMethod;
import com.italo.catalogy.model.enums.PaymentStatus;
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
import java.util.UUID;

@Entity
@Table(name = "tb_payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    @NotNull
    private OrderModel order;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod method;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "amount_initial", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountInitial;

    @Column(name = "installments")
    private Integer installments;


    @Digits(integer = 8, fraction = 2)
    @Column(name = "installments_value", precision = 10, scale = 2)
    private BigDecimal installmentsValue;


    @Digits(integer = 8, fraction = 2)
    @Column(name = "installments_fee", precision = 10, scale = 2)
    private BigDecimal installmentsFee;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "amount_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountTotal;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "amount_commission", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountCommission;

    @NotNull
    @Digits(integer = 8, fraction = 2)
    @Column(name = "amount_gateway", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountGateway;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "amount_seller", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountSeller;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "gateway", length = 100)
    private String gateway;

    @Column(name = "gateway_payment_id", length = 100)
    private String gatewayPaymentId;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "payment")
    private InvoiceModel invoice;




}
