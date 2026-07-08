package com.italo.catalogy.controller;

import com.italo.catalogy.dto.stock_order.CreateStockOrderRequestDTO;
import com.italo.catalogy.dto.stock_order.StockOrderResponseDTO;
import com.italo.catalogy.dto.stock_order_item.StockOrderItemResponseDTO;
import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;
import com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod.SupplierItemWithCprodResponseDTO;
import com.italo.catalogy.dto.tie_supplier_item.update_cprod.UpdateCprodOfSupplierItemsRequestDTO;
import com.italo.catalogy.mapper.StockOrderItemMapper;
import com.italo.catalogy.mapper.StockOrderMapper;
import com.italo.catalogy.mapper.SupplierItemMapper;
import com.italo.catalogy.model.StockOrderModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.StockOrderService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("stock-order")
public class StockOrderController {
    private final StockOrderService stockOrderService;
    private final StockOrderMapper stockOrderMapper;
    private final StockOrderItemMapper stockOrderItemMapper;
    private final SupplierItemMapper supplierItemMapper;

    public StockOrderController(StockOrderService stockOrderService, StockOrderMapper stockOrderMapper, StockOrderItemMapper stockOrderItemMapper, SupplierItemMapper supplierItemMapper) {
        this.stockOrderService = stockOrderService;
        this.stockOrderMapper = stockOrderMapper;
        this.stockOrderItemMapper = stockOrderItemMapper;
        this.supplierItemMapper = supplierItemMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockOrderResponseDTO> getStockOrderBYId(@PathVariable UUID id, @AuthenticationPrincipal UserModel userModel){
        StockOrderModel stockOrderModel = this.stockOrderService.getStockOrderById(id, userModel);

        List<StockOrderItemResponseDTO> items = stockOrderModel.getItems().stream()
            .map(item -> this.stockOrderItemMapper.modelToResponse(item))
            .toList();
        
        StockOrderResponseDTO response = this.stockOrderMapper.modelToResponse(stockOrderModel, items);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}/cprod-and-items")
    public ResponseEntity<SupplierItemWithCprodResponseDTO> getCprodAndItemsIdOfStockOrderById(
        @PathVariable UUID id,
        @AuthenticationPrincipal UserModel userModel
    ){
        SupplierItemWithCprodResponseDTO response = this.stockOrderService.getCprodAndItemsIdOfStockOrderById(id, userModel);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<StockOrderResponseDTO> createStockOrder (
            @RequestBody CreateStockOrderRequestDTO createStockOrderRequestDTO,
            @AuthenticationPrincipal UserModel userModel
            ){
        StockOrderModel stockOrderModel = this.stockOrderService.createStockOrder(createStockOrderRequestDTO, userModel);
        List<StockOrderItemResponseDTO> stockOrderItemsDTO = stockOrderModel.getItems().stream()
                .map(this.stockOrderItemMapper::modelToResponse)
                .toList();
        return ResponseEntity.ok(this.stockOrderMapper.modelToResponse(stockOrderModel, stockOrderItemsDTO));
    }

    @PostMapping("/input-invoice-xml")
    public ResponseEntity<SupplierItemWithCprodResponseDTO> inputInvoiceOfStockOrder(
            @AuthenticationPrincipal UserModel userModel,
            @RequestPart("invoiceXml") @NotNull MultipartFile invoiceXml,
            @RequestParam("stockOrderId") @NotNull UUID stockOrderId
            ){
        return ResponseEntity.ok(this.stockOrderService.inputInvoiceXml(userModel, invoiceXml, stockOrderId));
    }

    @PutMapping("/{id}/update-cprod-itens")
    public ResponseEntity<List<SupplierItemResponseDTO>> updateCprodOfSupplierItens(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserModel userModel,
            @RequestBody @NotNull UpdateCprodOfSupplierItemsRequestDTO updateCprodOfSupplierItemsRequestDTO
    ){
        StockOrderModel stockOrderModel = this.stockOrderService.updateCprodOfSupplierItems(id, userModel, updateCprodOfSupplierItemsRequestDTO);

        List<SupplierItemResponseDTO> response = stockOrderModel.getItems().stream()
                .map(stockOrderItemModel -> this.supplierItemMapper.modelToResponse(stockOrderItemModel.getSupplierItem()))
                .toList();

        return ResponseEntity.ok(response);
    }

}
