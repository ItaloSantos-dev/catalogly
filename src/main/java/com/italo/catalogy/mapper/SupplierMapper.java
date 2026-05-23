package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.supplier.CreateSupplierRequestDTO;
import com.italo.catalogy.dto.supplier.SupplierResponseDTO;
import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.SupplierModel;
import com.italo.catalogy.model.UserModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SupplierMapper {
    public SupplierModel createToModel(CreateSupplierRequestDTO createSupplierRequestDTO, SellerModel sellerModel){
        SupplierModel supplierModel = new SupplierModel();
        supplierModel.setSeller(sellerModel);
        supplierModel.setCnpj(createSupplierRequestDTO.cnpj());
        supplierModel.setName(createSupplierRequestDTO.name());
        supplierModel.setContactSupplierType(createSupplierRequestDTO.contactSuplierType());
        supplierModel.setContactValue(createSupplierRequestDTO.contactValue());
        supplierModel.setCreatedAt(LocalDateTime.now());
        supplierModel.setUpdatedAt(LocalDateTime.now());
        return supplierModel;
    }

    public SupplierResponseDTO modelToResponse(SupplierModel supplierModel, List<SupplierItemResponseDTO> items){
        return new SupplierResponseDTO(
                supplierModel.getName(),
                supplierModel.getCnpj(),
                supplierModel.getContactSupplierType(),
                supplierModel.getContactValue(),
                items
        );
    }
}
