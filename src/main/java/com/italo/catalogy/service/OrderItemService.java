package com.italo.catalogy.service;

import com.italo.catalogy.dto.ItemQuantity;
import com.italo.catalogy.mapper.OrderItemMapper;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.OrderItemModel;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.respository.ItemRepository;
import com.italo.catalogy.respository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemService(OrderItemRepository orderItemRepository, ItemRepository itemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    public List<OrderItemModel> createListOfOrderItems(List<ItemQuantity> itemsQuantity, OrderModel orderModel){
        return itemsQuantity.stream().
                map(itemQuantity -> {
                    ItemModel item = this.itemRepository.findById(itemQuantity.itemId())
                            .orElseThrow(() -> new RuntimeException("Deu ruin"));
                        OrderItemModel orderItemModel = new OrderItemModel();
                        return this.orderItemMapper.createToModel(item, itemQuantity, orderModel);
                    }
                )
                .toList();
    }
}
