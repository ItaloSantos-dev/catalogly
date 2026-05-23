package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.ItemQuantity;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.OrderItemModel;
import com.italo.catalogy.model.OrderModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemMapper {
    public OrderItemModel createToModel(ItemModel item, ItemQuantity itemQuantity, OrderModel orderModel){
        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setItem(item);
        orderItemModel.setAmount(itemQuantity.quantity());
        orderItemModel.setPriceUnique(item.getPrice());
        orderItemModel.setPriceFinal(item.getPrice().multiply(new BigDecimal(itemQuantity.quantity())));
        orderItemModel.setOrder(orderModel);
        return orderItemModel;
    }
}
