package com.italo.catalogy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_seller")
@AllArgsConstructor
@NoArgsConstructor
@Data
@PrimaryKeyJoinColumn(name = "id")
public class SellerModel extends UserModel{
    @NotBlank
    @CPF
    @Column(name = "cpf",length = 11, nullable = false, unique = true)
    private String cpf;

    @NotBlank
    @Size(min = 11, max = 11)
    @Pattern(regexp = "\\d{10,11}")
    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phone;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "seller")
    private CatalogModel catalog;

    @OneToMany(mappedBy = "seller")
    private List<OrderModel> ordersSelled;
}
