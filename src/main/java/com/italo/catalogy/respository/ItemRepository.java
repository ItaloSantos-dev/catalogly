package com.italo.catalogy.respository;

import com.italo.catalogy.model.ItemModel;
import io.minio.messages.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, UUID> {
    Boolean existsByNameAndCatalogSellerUserId(String name, UUID userId);
    List<ItemModel> findByCategoryId(UUID categoryId);
}
