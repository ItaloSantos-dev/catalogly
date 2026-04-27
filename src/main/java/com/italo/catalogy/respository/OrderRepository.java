package com.italo.catalogy.respository;

import com.italo.catalogy.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
    List<OrderModel> findByBuyerId(UUID buyerId);
}
