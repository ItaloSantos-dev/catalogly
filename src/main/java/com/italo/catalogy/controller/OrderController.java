package com.italo.catalogy.controller;

import com.italo.catalogy.dto.order.OrderResponseDTO;
import com.italo.catalogy.mapper.OrderMapper;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(@AuthenticationPrincipal UserModel userModel){
        List<OrderModel> orders = this.orderService.getMyOrders(userModel);
        List<OrderResponseDTO> response = orders.stream()
                .map(this.orderMapper::modelToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
