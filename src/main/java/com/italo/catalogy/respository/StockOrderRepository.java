package com.italo.catalogy.respository;

import com.italo.catalogy.model.StockOrderModel;
import com.italo.catalogy.model.SupplierItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockOrderRepository extends JpaRepository<StockOrderModel, UUID> {

}
