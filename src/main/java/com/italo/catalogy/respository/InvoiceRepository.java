package com.italo.catalogy.respository;

import com.italo.catalogy.model.InvoiceModel;
import com.italo.catalogy.model.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceModel, UUID> {

    @Query("SELECT MAX(i.rpsNumber) FROM InvoiceModel i")
    Integer findLastRps ();

    Integer countByOrderCatalogIdAndStatus(UUID catalogId, InvoiceStatus status);
}
