package com.italo.catalogy.respository;

import com.italo.catalogy.model.OrderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemModel, UUID> {
    boolean existsByItemId(UUID itemId);
}
