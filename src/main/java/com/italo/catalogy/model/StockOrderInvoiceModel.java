package com.italo.catalogy.model;

import com.italo.catalogy.dto.invoice_xml.InvoiceXmlDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_stock_order_invoice")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockOrderInvoiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "stock_order_id")
    private StockOrderModel stockOrderModel;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "invoice_xml_dto", columnDefinition = "jsonb")
    private InvoiceXmlDTO invoiceXmlDTO;

    @Column(name = "invoice_xml_path")
    private String invoice_xml_path;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


}
