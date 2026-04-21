package com.italo.catalogy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name="tb_category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "catalog_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
//adicionar constraint unique (name, catalog_id) e relacionar com N items
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private CatalogModel catalog;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category")
    private List<ItemModel> items;
}
