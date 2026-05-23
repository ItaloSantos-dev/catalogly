package com.italo.catalogy.service;

import com.italo.catalogy.mapper.SupplierItemMapper;
import com.italo.catalogy.model.*;
import com.italo.catalogy.respository.ItemRepository;
import com.italo.catalogy.respository.SellerRepository;
import com.italo.catalogy.respository.SupplierItemRepository;
import com.italo.catalogy.respository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierItemService {

    private final ItemRepository itemRepository;
    private final SupplierItemMapper supplierItemMapper;
    private final SupplierItemRepository supplierItemRepository;


    public SupplierItemService(ItemRepository itemRepository, SupplierItemMapper supplierItemMapper, SupplierItemRepository supplierItemRepository) {

        this.itemRepository = itemRepository;
        this.supplierItemMapper = supplierItemMapper;
        this.supplierItemRepository = supplierItemRepository;
    }

    public List<SupplierItemModel> createSupplierItems(SellerModel sellerModel, List<UUID> itemsId, SupplierModel supplierModel){
        if (!supplierModel.getSeller().getId().equals(sellerModel.getId()))
            throw new RuntimeException("Deu ruin");

        List<ItemModel> items = this.itemRepository.findAllByIdInAndCatalogId(itemsId, sellerModel.getCatalog().getId());

        if (items.size()!=itemsId.size())
            throw new RuntimeException("Deu ruin");

        List<SupplierItemModel> supplierItems = items.stream()
                .map(i -> this.supplierItemMapper.createToModel(i, supplierModel))
                .toList();

        return supplierItems;

    }
}
