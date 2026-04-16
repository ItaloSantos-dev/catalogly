package com.italo.catalogy.model;

import com.italo.catalogy.model.enums.ContactSuplierType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "tb_supplier",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "seller_id"}),
                @UniqueConstraint(columnNames = {"contact_value", "seller_id"}),
                @UniqueConstraint(columnNames = {"cnpj", "seller_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerModel seller;

    @NotBlank
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @CNPJ
    @Pattern(regexp = "\\d{14}")
    @Column(name = "cnpj", length = 14, nullable = false)
    private String cnpj;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_supplier_type", nullable = false)
    private ContactSuplierType contactSupplierType;

    @NotBlank
    @Column(name = "contact_value", nullable = false)
    private String contactValue;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;


    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "supplier")
    private List<SupplierItemModel> items;

    @OneToMany(mappedBy = "supplierModel")
    private List<StockOrderModel> stockOrders;
}
