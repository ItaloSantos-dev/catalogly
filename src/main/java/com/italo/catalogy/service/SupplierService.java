package com.italo.catalogy.service;

import com.italo.catalogy.dto.supplier.CreateSupplierRequestDTO;
import com.italo.catalogy.dto.supplier.SupplierResponseDTO;
import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;
import com.italo.catalogy.mapper.SupplierItemMapper;
import com.italo.catalogy.mapper.SupplierMapper;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.SupplierItemModel;
import com.italo.catalogy.model.SupplierModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.model.enums.UserRole;
import com.italo.catalogy.respository.SellerRepository;
import com.italo.catalogy.respository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierMapper supplierMapper;
    private final SellerRepository sellerRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierItemService supplierItemService;
    private final SupplierItemMapper supplierItemMapper;

    public SupplierService(SupplierMapper supplierMapper, SellerRepository sellerRepository, SupplierRepository supplierRepository, SupplierItemService supplierItemService, SupplierItemMapper supplierItemMapper) {
        this.supplierMapper = supplierMapper;
        this.sellerRepository = sellerRepository;
        this.supplierRepository = supplierRepository;
        this.supplierItemService = supplierItemService;
        this.supplierItemMapper = supplierItemMapper;
    }

    public SupplierResponseDTO createSupplier(CreateSupplierRequestDTO createSupplierRequestDTO, UserModel userModel){
        if (userModel.getRole()!= UserRole.SELLER)
            throw new RuntimeException("Deu ruin");

        SellerModel sellerModel = this.sellerRepository.findByUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (this.supplierRepository.existsByNameAndSellerId(createSupplierRequestDTO.name(), sellerModel.getId()))
            throw new RuntimeException("Deu ruin");

        if (this.supplierRepository.existsByCnpjAndSellerId(createSupplierRequestDTO.cnpj(), sellerModel.getId()))
            throw new RuntimeException("Deu ruin");

        if (this.supplierRepository.existsByContactValueAndSellerId(createSupplierRequestDTO.contactValue(), sellerModel.getId()))
            throw new RuntimeException("Deu ruin");

        SupplierModel supplierModel = this.supplierMapper.createToModel(createSupplierRequestDTO, sellerModel);

        if (createSupplierRequestDTO.items()!=null){
            List<SupplierItemModel> supplierItems = this.supplierItemService.createSupplierItems(sellerModel, createSupplierRequestDTO.items(), supplierModel);
            supplierModel.setItems(supplierItems);
        }
        this.supplierRepository.save(supplierModel);

        List<SupplierItemResponseDTO> supplierItemsResponse = supplierModel.getItems() == null ?
                null :
                supplierModel.getItems().stream()
                        .map(this.supplierItemMapper::modelToResponse)
                        .toList();

        return this.supplierMapper.modelToResponse(supplierModel, supplierItemsResponse);
    }

    public List<SupplierModel> getSuppliersOfCatalogById(UserModel userModel){
        return this.supplierRepository.findAllBySellerUserId(userModel.getId());
    }
}
