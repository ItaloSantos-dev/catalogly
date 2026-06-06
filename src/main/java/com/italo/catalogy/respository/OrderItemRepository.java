package com.italo.catalogy.respository;

import com.italo.catalogy.model.OrderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemModel, UUID> {
    boolean existsByItemId(UUID itemId);

    @Query(value = "SELECT oi.item_id, i.name, SUM(oi.amount) as quantity_sold, SUM(oi.price_final) as revenue " +
                   "FROM tb_order_item oi " +
                   "JOIN tb_item i ON oi.item_id = i.id " +
                   "JOIN tb_order o ON oi.order_id = o.id " +
                   "WHERE o.catalog_id = :catalogId AND o.status = 'COMPLETED' " +
                   "GROUP BY oi.item_id, i.name " +
                   "ORDER BY SUM(oi.amount) DESC " +
                   "LIMIT 10", nativeQuery = true)
    List<Object[]> findTopItemsByCatalogId(@Param("catalogId") UUID catalogId);
}
