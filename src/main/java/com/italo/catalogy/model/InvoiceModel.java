package com.italo.catalogy.model;

import com.italo.catalogy.model.enums.InvoiceStatus;
import com.italo.catalogy.model.enums.PaymentMethod;
import com.italo.catalogy.model.enums.SefazEnvironment;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_invoice")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private UserModel customer;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderModel order;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentModel payment;

    @NotBlank
    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @NotBlank
    @Column(name = "series", nullable = false, length = 45)
    private String series;

    @NotBlank
    @Column(name = "key", nullable = false, unique = true, length = 44)
    private String key;

    @Column(name = "model")
    private Integer model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "sefaz_environment", nullable = false)
    private SefazEnvironment sefazEnvironment;

    @NotBlank
    @Column(name="inssuer_cnpj", nullable = false, length = 14)
    private String  inssuerCnpj;

    @NotBlank
    @Column(name = "inssuer_name", nullable = false)
    private String inssuerName;

    @Column(name = "inssuer_state_registration", length = 30)
    private String inssuerStateRegistration;

    @Column(name = "inssuer_trade_name")
    private String inssuerTradeName;

    @NotBlank
    @Column(name = "customer_cpf_cnpj", nullable = false, length = 14)
    private String customerCpfCnpj;

    @NotBlank
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @NotBlank
    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @NotBlank
    @Column(name = "billing_address", nullable = false)
    private String  billingAddress;

    @NotBlank
    @Column(name = "shipping_address", nullable = false)
    private String  shippingAddress;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "subtotal_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotalAmount;

    @NotNull
    @Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @NotNull
    @Column(name = "shipping_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingAmount;

    @NotNull
    @Column(name = "tax_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "xml_send", columnDefinition = "TEXT")
    private String xmlSend;

    @Column(name = "xml_authorized", columnDefinition = "TEXT")
    private String xmlAuthorized;

    @Column(name = "sefaz_protocol", length = 50)
    private String sefazProtocol;

    @Column(name = "sefaz_authorization_date")
    private LocalDateTime sefazAuthorizationDate;

    @NotBlank
    @Column(name = "sefaz_status_code", length = 10, nullable = false)
    private String sefazStatusCode;

    @Column(name = "digest_value")
    private String digestValue;

    @Column(name = "qr_code_url", columnDefinition = "TEXT")
    private String qrCodeUrl;

    @Column(name = "danfe_url", columnDefinition = "TEXT")
    private String danfeUrl;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "cancellation_protocol")
    private String cancellationProtocol;


    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


}
