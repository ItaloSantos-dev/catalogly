package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.invoice_xml.Det;
import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;
import com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod.SupplierItemWithCprodResponseDTO;
import com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod.TieItemInvoice;
import com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod.TieItemStockOrder;
import com.italo.catalogy.model.*;
import com.italo.catalogy.service.ImageService;
import com.italo.catalogy.service.XmlService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SupplierItemMapper {
    private final ItemMapper itemMapper;
    private final ImageService imageService;
    private final XmlService xmlService;

    public SupplierItemMapper(ItemMapper itemMapper, ImageService imageService, XmlService xmlService) {
        this.itemMapper = itemMapper;
        this.imageService = imageService;
        this.xmlService = xmlService;
    }

    public SupplierItemModel createToModel(ItemModel itemModel, SupplierModel supplierModel){
        SupplierItemModel supplierItemModel = new SupplierItemModel();
        supplierItemModel.setSupplier(supplierModel);
        supplierItemModel.setItem(itemModel);
        supplierItemModel.setCreatedAt(LocalDateTime.now());
        return supplierItemModel;
    }

    public SupplierItemResponseDTO modelToResponse(SupplierItemModel supplierItemModel){
        return new SupplierItemResponseDTO(
                supplierItemModel.getId(),
                supplierItemModel.getSupplier().getName(),
                supplierItemModel.getSupplier().getId(),
                this.itemMapper.modelToResponse(supplierItemModel.getItem()),
                supplierItemModel.getSupplierItemCode()==null? null : supplierItemModel.getSupplierItemCode(),
                supplierItemModel.getLastPrice()==null? null : supplierItemModel.getLastPrice()
        );
    }

    public SupplierItemWithCprodResponseDTO listToCprodAndSupplierItemWithCprodResponseDTO(List<Det> invoiceProducts, StockOrderModel stockOrderModel){

        List<TieItemInvoice> invoiceProductList = invoiceProducts.stream()
                .map(invoiceProduct -> new TieItemInvoice(
                        invoiceProduct.prod().cProd(),
                        invoiceProduct.prod().xProd(),
                        invoiceProduct.prod().vUnCom()
                ))
                .toList();

        List<TieItemStockOrder> stockOrderItems = stockOrderModel.getItems().stream()
                .map(stockOrderItem -> new TieItemStockOrder(
                        stockOrderItem.getId(),
                        stockOrderItem.getSupplierItem().getItem().getName(),
                        this.imageService.getAssignedUrlImage(stockOrderItem.getSupplierItem().getItem().getImagePath1())
                ))
                .toList();

        return new SupplierItemWithCprodResponseDTO(stockOrderItems, invoiceProductList, this.xmlService.getAssignedUrlXml(stockOrderModel.getStockOrderInvoiceModel().getInvoice_xml_path()), stockOrderModel.getId(), stockOrderModel.getSupplierModel().getId());

        //Arrumar os nomes de amarração  verificar a logica dos detalhes de qual item retorna, o item do catalogo ou o item do supplier
    }
}
