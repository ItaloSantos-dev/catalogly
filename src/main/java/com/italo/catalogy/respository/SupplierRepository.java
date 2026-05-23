package com.italo.catalogy.respository;

import com.italo.catalogy.model.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<SupplierModel, UUID> {
    Boolean existsByNameAndSellerId(String name, UUID sellerId);

    Boolean existsByCnpjAndSellerId(String cnpj, UUID sellerId);

    Boolean existsByContactValueAndSellerId(String contactValue, UUID sellerId);

}
