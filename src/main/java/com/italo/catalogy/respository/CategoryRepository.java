package com.italo.catalogy.respository;

import com.italo.catalogy.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    Boolean existsByNameAndCatalogSellerUserIdAndIdNot(String name, UUID userId, UUID id);
    Integer countByCatalogId(UUID catalogId);
    List<CategoryModel> findByCatalogId(UUID catalogId);
}
