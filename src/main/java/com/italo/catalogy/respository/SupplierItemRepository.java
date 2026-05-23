package com.italo.catalogy.respository;

import com.italo.catalogy.model.SupplierItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SupplierItemRepository extends JpaRepository<SupplierItemModel, UUID> {
    List<SupplierItemModel> findAllByIdInAndSupplierId(List<UUID> ids, UUID supplierId );
}
