package com.italo.catalogy.respository;

import com.italo.catalogy.model.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
public interface SellerRepository extends JpaRepository<SellerModel, UUID> {
    Boolean existsByUserEmail(String email);
    Optional<SellerModel> findByUserId(UUID id);
}

