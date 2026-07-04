package com.italo.catalogy.controller;

import com.italo.catalogy.dto.stock_order.StockOrderResponseDTO;
import com.italo.catalogy.dto.stock_order_item.StockOrderItemResponseDTO;
import com.italo.catalogy.dto.supplier.CreateSupplierRequestDTO;
import com.italo.catalogy.dto.supplier.SupplierResponseDTO;
import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;
import com.italo.catalogy.mapper.StockOrderItemMapper;
import com.italo.catalogy.mapper.StockOrderMapper;
import com.italo.catalogy.mapper.SupplierItemMapper;
import com.italo.catalogy.model.StockOrderItemModel;
import com.italo.catalogy.model.StockOrderModel;
import com.italo.catalogy.model.SupplierItemModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.SupplierService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("supplier")
public class SupplierController {
    private final SupplierService supplierService;
    private final SupplierItemMapper supplierItemMapper;
    private final StockOrderItemMapper stockOrderItemMapper;
    private final StockOrderMapper stockOrderMapper;

    public SupplierController(SupplierService supplierService, SupplierItemMapper supplierItemMapper, StockOrderItemMapper stockOrderItemMapper, StockOrderMapper stockOrderMapper) {
        this.supplierService = supplierService;
        this.supplierItemMapper = supplierItemMapper;
        this.stockOrderItemMapper = stockOrderItemMapper;
        this.stockOrderMapper = stockOrderMapper;
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(
            @AuthenticationPrincipal UserModel userModel,
            @RequestBody CreateSupplierRequestDTO createSupplierRequestDTO
            ){
        return ResponseEntity.ok(this.supplierService.createSupplier(createSupplierRequestDTO, userModel));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<SupplierItemResponseDTO>> getItemsOfSupplierById(@PathVariable UUID id, @AuthenticationPrincipal UserModel userModel){
        List<SupplierItemModel> items = this.supplierService.getItemsOfSupplierById(id, userModel);

        List<SupplierItemResponseDTO> response = items.stream()
            .map(item -> this.supplierItemMapper.modelToResponse(item))
            .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/stock-orders")
    public ResponseEntity<List<StockOrderResponseDTO>> getStockOrdersOfSupplierById(@PathVariable UUID id, @AuthenticationPrincipal UserModel userModel){
        List<StockOrderModel> stockOrders = this.supplierService.getStockOrdersOfSupplierById(id, userModel);
        List<StockOrderResponseDTO> response = new ArrayList<>();

        for (StockOrderModel stockOrderModel : stockOrders) {
            List<StockOrderItemResponseDTO> items = stockOrderModel.getItems().stream()
                .map(item -> this.stockOrderItemMapper.modelToResponse(item))
                .toList();
            response.add(this.stockOrderMapper.modelToResponse(stockOrderModel, items));
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplierById(@PathVariable UUID id, @AuthenticationPrincipal UserModel userModel){
        this.supplierService.updateActiveSupplierById(id, userModel);
        return ResponseEntity.noContent().build();
    }
}
