package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.ItemQuantity;
import com.italo.catalogy.dto.order_item.OrderItemResponseDTO;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.OrderItemModel;
import com.italo.catalogy.model.OrderModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemMapper {
    private final ItemMapper itemMapper;

    public OrderItemMapper(ItemMapper itemMapper){
        this.itemMapper = itemMapper;
    }


    public OrderItemModel createToModel(ItemModel item, ItemQuantity itemQuantity, OrderModel orderModel){
        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setItem(item);
        orderItemModel.setAmount(itemQuantity.quantity());
        orderItemModel.setPriceUnique(item.getPrice());
        orderItemModel.setPriceFinal(item.getPrice().multiply(new BigDecimal(itemQuantity.quantity())));
        orderItemModel.setOrder(orderModel);
        return orderItemModel;
    }

    public OrderItemResponseDTO modelToResponse(OrderItemModel orderItemModel){
        return new OrderItemResponseDTO(
            orderItemModel.getId(),
            orderItemModel.getPriceUnique(),
            orderItemModel.getAmount(),
            orderItemModel.getPriceFinal(),
            this.itemMapper.modelToResponse(orderItemModel.getItem())
        );
    }
}
