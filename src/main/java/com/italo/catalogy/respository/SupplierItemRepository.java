package com.italo.catalogy.respository;

import com.italo.catalogy.model.SupplierItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierItemRepository extends JpaRepository<SupplierItemModel, UUID> {
}
