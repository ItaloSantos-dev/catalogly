package com.italo.catalogy.respository;

import com.italo.catalogy.model.CouponModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<CouponModel, UUID> {
    Boolean existsBySlugAndCatalogId(String slug, UUID id);
    Optional<CouponModel> findBySlugAndCatalogId(String slug, UUID id);
    Integer countByCatalogIdAndActiveTrue(UUID catalogId);
}
