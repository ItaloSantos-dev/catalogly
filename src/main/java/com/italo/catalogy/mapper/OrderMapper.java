package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.order.CreateOrderRequestDTO;
import com.italo.catalogy.dto.order.OrderResponseDTO;
import com.italo.catalogy.model.*;
import com.italo.catalogy.model.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {
    public OrderResponseDTO modelToResponse(OrderModel orderModel, String paymentLink){
        return new OrderResponseDTO(
                orderModel.getId(),
                orderModel.getStatus(),
                orderModel.getCoupon()==null? null : orderModel.getCoupon().getId(),
                orderModel.getCoupon()==null? null : orderModel.getCoupon().getSlug(),
                orderModel.getCouponDiscount(),
                orderModel.getPriceInitial(),
                null,
                paymentLink
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
