package com.italo.catalogy.service;

import com.italo.catalogy.dto.ItemQuantity;
import com.italo.catalogy.dto.stock_order.CreateStockOrderRequestDTO;
import com.italo.catalogy.mapper.StockOrderMapper;
import com.italo.catalogy.model.*;
import com.italo.catalogy.respository.SellerRepository;
import com.italo.catalogy.respository.StockOrderRepository;
import com.italo.catalogy.respository.SupplierItemRepository;
import com.italo.catalogy.respository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class StockOrderService {
    private final StockOrderItemService stockOrderItemService;
    private final SellerRepository sellerRepository;
    private final StockOrderMapper stockOrderMapper;
    private final SupplierRepository supplierRepository;
    private final StockOrderRepository stockOrderRepository;

    public StockOrderService(StockOrderItemService stockOrderItemService, SellerRepository sellerRepository, StockOrderMapper stockOrderMapper, SupplierRepository supplierRepository, StockOrderRepository stockOrderRepository) {
        this.stockOrderItemService = stockOrderItemService;
        this.sellerRepository = sellerRepository;
        this.stockOrderMapper = stockOrderMapper;
        this.supplierRepository = supplierRepository;
        this.stockOrderRepository = stockOrderRepository;
    }


    public StockOrderModel createStockOrder(CreateStockOrderRequestDTO createStockOrderRequestDTO, UserModel userModel){
        SellerModel sellerModel = this.sellerRepository.findByUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        //Busco pelo id and seller id | Confirmo que o supplier seja realmente do seller
        SupplierModel supplierModel = this.supplierRepository.findByIdAndSellerId(createStockOrderRequestDTO.supplierId(), sellerModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));


        StockOrderModel stockOrderModel = this.stockOrderMapper.createToModel(sellerModel, supplierModel);
        //Criar stockOrderItems
        List<StockOrderItemModel> stockOrderItems = this.stockOrderItemService.createStockOrderItems(createStockOrderRequestDTO, stockOrderModel);

        int itemsKnow = 0;
        int itemsAmount = 0;
        BigDecimal priceStimated = BigDecimal.ZERO;
        for (StockOrderItemModel  stockOrderItemModel: stockOrderItems){
            itemsAmount += stockOrderItemModel.getAmount();
            if (stockOrderItemModel.getSupplierItem().getSupplierItemCode()!=null){
                itemsKnow++;
                priceStimated = priceStimated.add(stockOrderItemModel.getSupplierItem().getLastPrice());
            }
        }

        stockOrderModel.setItems(stockOrderItems);
        stockOrderModel.setItemsKnow(itemsKnow);
        stockOrderModel.setPriceEstimated(priceStimated);
        stockOrderModel.setItemsAmount(itemsAmount);



        return this.stockOrderRepository.save(stockOrderModel);

    }
}
