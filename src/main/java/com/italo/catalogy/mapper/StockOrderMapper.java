package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.stock_order.CreateStockOrderRequestDTO;
import com.italo.catalogy.dto.stock_order.StockOrderResponseDTO;
import com.italo.catalogy.dto.stock_order_item.StockOrderItemResponseDTO;
import com.italo.catalogy.model.*;
import com.italo.catalogy.model.enums.StockOrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class StockOrderMapper {
    public StockOrderModel createToModel(SellerModel sellerModel, SupplierModel supplierModel){
        StockOrderModel stockOrderModel = new StockOrderModel();
        stockOrderModel.setCreatedAt(LocalDateTime.now());
        stockOrderModel.setSellerModel(sellerModel);
        stockOrderModel.setUpdatedAt(LocalDateTime.now());
        stockOrderModel.setStatus(StockOrderStatus.PENDING);
        stockOrderModel.setSupplierModel(supplierModel);
        return stockOrderModel;
    }

    public StockOrderResponseDTO modelToResponse (StockOrderModel stockOrderModel, List<StockOrderItemResponseDTO> supplierItems){
        return new StockOrderResponseDTO(
                stockOrderModel.getId(),
                stockOrderModel.getSellerModel().getId(),
                stockOrderModel.getSellerModel().getUser().getFirstName() + " " + stockOrderModel.getSellerModel().getUser().getLastName(),
                stockOrderModel.getSupplierModel().getId(),
                stockOrderModel.getSupplierModel().getName(),
                stockOrderModel.getItemsAmount(),
                stockOrderModel.getStatus(),
                stockOrderModel.getPriceEstimated()==null ? null : stockOrderModel.getPriceEstimated(),
                stockOrderModel.getPriceFinal()==null ? null : stockOrderModel.getPriceFinal(),
                stockOrderModel.getStockOrderInvoiceModel()==null?
                        null : stockOrderModel.getStockOrderInvoiceModel().getInvoice_xml_path(),
                supplierItems
        );
    }
}
