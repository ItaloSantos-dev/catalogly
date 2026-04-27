package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.order.OrderResponseDTO;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.PaymentModel;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponseDTO modelToResponse(OrderModel orderModel){
        return new OrderResponseDTO(
                orderModel.getId(),
                orderModel.getStatus(),
                orderModel.getCoupon()==null? null : orderModel.getCoupon().getId(),
                orderModel.getCoupon()==null? null : orderModel.getCoupon().getSlug(),
                orderModel.getPriceFinal().subtract(orderModel.getPriceInitial()),
                orderModel.getPriceInitial(),
                orderModel.getPriceFinal()
        );
    }
}
