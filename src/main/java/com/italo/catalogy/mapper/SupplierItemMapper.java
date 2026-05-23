package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.item.ItemResponseDTO;
import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.SupplierItemModel;
import com.italo.catalogy.model.SupplierModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SupplierItemMapper {
    private final ItemMapper itemMapper;

    public SupplierItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
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
                supplierItemModel.getItem().getName(),
                this.itemMapper.modelToResponse(supplierItemModel.getItem()),
                supplierItemModel.getSupplierItemCode()==null? null : supplierItemModel.getSupplierItemCode()
        );
    }
}
