package com.italo.catalogy.respository;

import com.italo.catalogy.model.CatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogModel, UUID> {

    Optional<CatalogModel> findBySlug(String slug);
    Boolean existsBySlug(String slug);
    Boolean existsByNameAndSellerUserId(String name, UUID userId);
    Optional<CatalogModel>findBySellerUserId(UUID sellerId);
}
