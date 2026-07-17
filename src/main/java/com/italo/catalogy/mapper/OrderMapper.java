package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.order.CreateOrderRequestDTO;
import com.italo.catalogy.dto.order.OrderResponseDTO;
import com.italo.catalogy.dto.order_item.OrderItemResponseDTO;
import com.italo.catalogy.model.*;
import com.italo.catalogy.model.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper){
        this.orderItemMapper = orderItemMapper;
    }
    public OrderResponseDTO modelToResponse(OrderModel orderModel, String paymentLink){
        BigDecimal priceFinal = BigDecimal.ZERO;
        List<OrderItemResponseDTO> items = new ArrayList<>();

        for (OrderItemModel orderItemModel : orderModel.getItens()) {
            priceFinal = priceFinal.add(orderItemModel.getPriceFinal());
            items.add(this.orderItemMapper.modelToResponse(orderItemModel));
        }

        return new OrderResponseDTO(
                orderModel.getId(),
                orderModel.getCatalog().getName(),
                orderModel.getStatus(),
                orderModel.getCoupon()==null? null : orderModel.getCoupon().getId(),
                orderModel.getCoupon()==null? null : orderModel.getCoupon().getSlug(),
                orderModel.getCouponDiscount(),
                orderModel.getPriceInitial(),
                priceFinal,
                paymentLink,
                items
        );
    }

    public OrderModel createToModel(
            UserModel buyer,
            CouponModel couponModel,
            CatalogModel catalogModel

    ){


        OrderModel orderModel = new OrderModel();
        orderModel.setBuyer(buyer);
        orderModel.setCatalog(catalogModel);
        orderModel.setSeller(catalogModel.getSeller());
        orderModel.setCoupon(couponModel);
        orderModel.setStatus(OrderStatus.CREATED);
        orderModel.setCreatedAt(LocalDateTime.now());
        orderModel.setUpdatedAt(LocalDateTime.now());

        return orderModel;

    }
}
