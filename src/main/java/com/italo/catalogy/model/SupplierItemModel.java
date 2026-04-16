package com.italo.catalogy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_supplier_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierItemModel {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;

     @ManyToOne
     @JoinColumn(name = "supplier_id", nullable = false)
     private SupplierModel supplier;

     @ManyToOne
     @JoinColumn(name = "item_id", nullable = false)
     private ItemModel item;

     @Column(name = "supplier_item_code")
     private String supplierItemCode;

     @Column(name = "last_price")
     private BigDecimal lastPrice;

     @OneToMany(mappedBy = "supplierItem")
     private List<StockOrderItemModel> orders;

}
