package com.italo.catalogy.respository;

import com.italo.catalogy.model.StockOrderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockOrderItemRepository extends JpaRepository<StockOrderItemModel, UUID> {
}
