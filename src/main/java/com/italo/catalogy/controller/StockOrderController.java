package com.italo.catalogy.controller;

import com.italo.catalogy.dto.stock_order.CreateStockOrderRequestDTO;
import com.italo.catalogy.dto.stock_order.StockOrderResponseDTO;
import com.italo.catalogy.dto.stock_order_item.StockOrderItemResponseDTO;
import com.italo.catalogy.mapper.StockOrderItemMapper;
import com.italo.catalogy.mapper.StockOrderMapper;
import com.italo.catalogy.model.StockOrderModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.StockOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stock-order")
public class StockOrderController {
    private final StockOrderService stockOrderService;
    private final StockOrderMapper stockOrderMapper;
    private final StockOrderItemMapper stockOrderItemMapper;

    public StockOrderController(StockOrderService stockOrderService, StockOrderMapper stockOrderMapper, StockOrderItemMapper stockOrderItemMapper) {
        this.stockOrderService = stockOrderService;
        this.stockOrderMapper = stockOrderMapper;
        this.stockOrderItemMapper = stockOrderItemMapper;
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
}
