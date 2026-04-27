package com.italo.catalogy.service;

import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderModel> getMyOrders(UserModel userModel){
        return orderRepository.findByBuyerId(userModel.getId());
    }
}
