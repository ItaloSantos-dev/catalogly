package com.italo.catalogy.service;

import com.italo.catalogy.dto.ItemQuantity;
import com.italo.catalogy.dto.stock_order.CreateStockOrderRequestDTO;
import com.italo.catalogy.mapper.StockOrderItemMapper;
import com.italo.catalogy.model.*;
import com.italo.catalogy.respository.StockOrderItemRepository;
import com.italo.catalogy.respository.SupplierItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StockOrderItemService {
    private final StockOrderItemRepository stockOrderItemRepository;
    private final SupplierItemRepository supplierItemRepository;
    private final StockOrderItemMapper stockOrderItemMapper;

    public StockOrderItemService(StockOrderItemRepository stockOrderItemRepository, SupplierItemRepository supplierItemRepository, StockOrderItemMapper stockOrderItemMapper) {
        this.stockOrderItemRepository = stockOrderItemRepository;
        this.supplierItemRepository = supplierItemRepository;
        this.stockOrderItemMapper = stockOrderItemMapper;
    }

    public List<StockOrderItemModel> createStockOrderItems (CreateStockOrderRequestDTO createStockOrderRequestDTO, StockOrderModel stockOrderModel){

        Map<UUID, Integer> quantitys = new HashMap<>();
        List<UUID> itemsId = new ArrayList<>();

        createStockOrderRequestDTO.items().forEach( itemQuantity -> {
            itemsId.add(itemQuantity.itemId());
            quantitys.put(itemQuantity.itemId(), itemQuantity.quantity());
        });


        //Busco pelo id e pelo supplier id | Garanto que cada item seja do supplier que veio o id na request
        List<SupplierItemModel> supplierItems = this.supplierItemRepository.findAllByIdInAndSupplierId(itemsId, createStockOrderRequestDTO.supplierId());

        if (itemsId.size()!= supplierItems.size())
            throw new RuntimeException("Deu ruin");

        return supplierItems.stream()
                .map(supplierItemModel -> {
                    Integer amount = quantitys.get(supplierItemModel.getId());
                    return this.stockOrderItemMapper.createToModel(stockOrderModel, supplierItemModel, amount);
                })
                .toList();

    }
}
