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
        name="tb_item",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "catalog_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
//adicionar constraint unique (name, catalog_id) e relacionar com 1 catalog e 1 category
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private CatalogModel catalog;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "about")
    private String about;

    @DecimalMin("0.01")
    @DecimalMax("99999999.99")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "image_path1", nullable = false)
    private String imagePath1;

    @Column(name = "image_path2")
    private String imagePath2;

    @Column(name = "image_path3")
    private String imagePath3;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "item")
    private List<OrderItemModel> orders;

    @OneToMany(mappedBy = "item")
    private List<SupplierItemModel> suppliers;
}
