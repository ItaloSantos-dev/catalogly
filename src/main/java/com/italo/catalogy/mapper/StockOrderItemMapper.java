package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.stock_order_item.StockOrderItemResponseDTO;
import com.italo.catalogy.model.StockOrderItemModel;
import com.italo.catalogy.model.StockOrderModel;
import com.italo.catalogy.model.SupplierItemModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StockOrderItemMapper {
    private SupplierItemMapper supplierItemMapper;

    StockOrderItemMapper (SupplierItemMapper supplierItemMapper){
        this.supplierItemMapper = supplierItemMapper;
    }

    public StockOrderItemModel createToModel(StockOrderModel stockOrderModel, SupplierItemModel supplierItemModel, Integer amount){
        BigDecimal priceUnique = null;
        BigDecimal priceFinal = null;

        if (supplierItemModel.getLastPrice()!=null){
            priceUnique = supplierItemModel.getLastPrice();
            priceFinal = supplierItemModel.getLastPrice().multiply(new BigDecimal(amount));
        }

        StockOrderItemModel stockOrderItemModel = new StockOrderItemModel();
        stockOrderItemModel.setStockOrder(stockOrderModel);
        stockOrderItemModel.setSupplierItem(supplierItemModel);
        stockOrderItemModel.setAmount(amount);
        stockOrderItemModel.setPriceUnique(priceUnique);
        stockOrderItemModel.setPriceFinal(priceFinal);

        return stockOrderItemModel;

    }

    public StockOrderItemResponseDTO modelToResponse (StockOrderItemModel stockOrderItemModel){
        return new StockOrderItemResponseDTO(
                this.supplierItemMapper.modelToResponse(stockOrderItemModel.getSupplierItem()),
                stockOrderItemModel.getAmount(),
                stockOrderItemModel.getPriceUnique(),
                stockOrderItemModel.getPriceFinal()
        );
    }
}
