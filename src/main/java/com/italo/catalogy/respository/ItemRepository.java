package com.italo.catalogy.respository;

import com.italo.catalogy.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, UUID> {
    Boolean existsByNameAndCatalogSellerUserId(String name, UUID userId);
    List<ItemModel> findByCategoryId(UUID categoryId);
    List<ItemModel> findAllByIdInAndCatalogId(List<UUID> ids, UUID sellerId);
    Integer countByCatalogIdAndDeletedFalse(UUID catalogId);
    List<ItemModel> findByCatalogIdAndDeletedFalse(UUID catalogId);
    Integer countByCatalogIdAndStockLessThanEqualAndDeletedFalse(UUID catalogId, Integer stock);
    Integer countByCatalogIdAndStockLessThanEqualAndDeletedFalse(UUID catalogId, Integer stock, Integer maxStock);

    @Query("SELECT COALESCE(SUM(i.price * i.stock), 0) FROM ItemModel i WHERE i.catalog.id = :catalogId AND i.deleted = false")
    BigDecimal sumStockValueByCatalogId(UUID catalogId);

    List<ItemModel> findByCatalogSellerUserId(UUID userId);
}
