package com.italo.catalogy.respository;

import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
    List<OrderModel> findByBuyerId(UUID buyerId);
    List<OrderModel> findByCatalogId(UUID catalogId);
    Integer countByCatalogId(UUID catalogId);
    Integer countByCatalogIdAndStatus(UUID catalogId, OrderStatus status);
    List<OrderModel> findByCatalogIdOrderByCreatedAtDesc(UUID catalogId);

    @Query("SELECT COALESCE(SUM(o.priceFinal), 0) FROM OrderModel o WHERE o.catalog.id = :catalogId AND o.status = :status")
    BigDecimal sumPriceFinalByCatalogIdAndStatus(UUID catalogId, OrderStatus status);
}
