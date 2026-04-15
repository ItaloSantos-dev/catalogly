package com.italo.catalogy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="tb_catalog")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CatalogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "seller_id", unique = true)
    private SellerModel seller;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name="slogan")
    private String slogan;

    @Column(name = "about")
    private String about;


    @Column(name = "fisic_address")
    private String fisicAddress;

    @NotBlank
    @Size(min = 11, max = 11)
    @Pattern(regexp = "\\d{10,11}")
    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phone;


    @Column(name = "image_icon_path")
    private String imageIconPath;

    @Column(name = "image_banner_path")
    private String imageBannerPath;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "catalog", fetch = FetchType.LAZY)
    private List<ItemModel> items;

    @OneToMany(mappedBy = "catalog", fetch = FetchType.LAZY)
    private List<CategoryModel> categorys;

}
