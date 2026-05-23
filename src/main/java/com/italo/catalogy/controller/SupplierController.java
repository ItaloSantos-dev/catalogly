package com.italo.catalogy.controller;

import com.italo.catalogy.dto.supplier.CreateSupplierRequestDTO;
import com.italo.catalogy.dto.supplier.SupplierResponseDTO;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(
            @AuthenticationPrincipal UserModel userModel,
            @RequestBody CreateSupplierRequestDTO createSupplierRequestDTO
            ){
        return ResponseEntity.ok(this.supplierService.createSupplier(createSupplierRequestDTO, userModel));
    }
}
