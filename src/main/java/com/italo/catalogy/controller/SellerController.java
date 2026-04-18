package com.italo.catalogy.controller;

import com.italo.catalogy.dto.seller.CreateSellerRequestDTO;
import com.italo.catalogy.dto.seller.SellerResponseDTO;
import com.italo.catalogy.mapper.SellerMapper;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.SellerService;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seller")
public class SellerController {

    private final SellerService sellerService;
    private final SellerMapper sellerMapper;

    public SellerController(SellerService sellerService, SellerMapper sellerMapper) {
        this.sellerService = sellerService;
        this.sellerMapper = sellerMapper;
    }

    @PostMapping()
    public ResponseEntity<SellerResponseDTO> register(
            @RequestBody CreateSellerRequestDTO createSellerRequestDTO,
            @Nullable @AuthenticationPrincipal UserModel userModel
            ){
        SellerModel newSeller = this.sellerService.createSeller(createSellerRequestDTO, userModel);

        return ResponseEntity.ok(this.sellerMapper.modelToResponse(newSeller));
    }
}
