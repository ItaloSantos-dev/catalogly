package com.italo.catalogy.controller;

import com.italo.catalogy.dto.catalog.CatalogPrivateResponseDTO;
import com.italo.catalogy.dto.seller.CreateSellerRequestDTO;
import com.italo.catalogy.dto.seller.SellerResponseDTO;
import com.italo.catalogy.mapper.CatalogMapper;
import com.italo.catalogy.mapper.SellerMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.CatalogService;
import com.italo.catalogy.service.SellerService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("seller")
public class SellerController {

    private final SellerService sellerService;
    private final SellerMapper sellerMapper;
    private final CatalogService catalogService;
    private final CatalogMapper catalogMapper;

    public SellerController(SellerService sellerService, SellerMapper sellerMapper, CatalogService catalogService, CatalogMapper catalogMapper) {
        this.sellerService = sellerService;
        this.sellerMapper = sellerMapper;
        this.catalogService = catalogService;
        this.catalogMapper = catalogMapper;
    }

    @PostMapping()
    public ResponseEntity<SellerResponseDTO> register(
            @RequestBody CreateSellerRequestDTO createSellerRequestDTO,
            @Nullable @AuthenticationPrincipal UserModel userModel
            ){
        SellerModel newSeller = this.sellerService.createSeller(createSellerRequestDTO, userModel);

        return ResponseEntity.ok(this.sellerMapper.modelToResponse(newSeller));
    }

    @GetMapping("/catalog")
    public ResponseEntity<CatalogPrivateResponseDTO> getMyCatalog(@NotNull @AuthenticationPrincipal UserModel userModel){
        CatalogModel catalogModel = this.catalogService.getBySellerId(userModel.getId());
        return ResponseEntity.ok(this.catalogMapper.modelToPrivateResponse(catalogModel));
    }

    //Arrumar depois - SOFT DELETE
    @DeleteMapping("/catalog")
    public ResponseEntity<Void> deleteMyCatalog(@AuthenticationPrincipal UserModel userModel){
        this.sellerService.deleteCatalogBySellerId(userModel.getId());
        return ResponseEntity.noContent().build();
    }
}
