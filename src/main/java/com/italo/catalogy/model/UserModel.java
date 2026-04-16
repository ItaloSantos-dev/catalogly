package com.italo.catalogy.model;

import com.italo.catalogy.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @NotBlank
    @Email
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min=8)
    @Column(name = "password",length = 100, nullable = false)
    private String password;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;


    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;


    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "buyer")
    private List<OrderModel> orders;

    @OneToMany(mappedBy = "customer")
    private List<InvoiceModel> invoices;

}
