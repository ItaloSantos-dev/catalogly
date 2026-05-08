package com.italo.catalogy.respository;

import com.italo.catalogy.model.PaymentModel;
import com.italo.catalogy.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, UUID >{
    Boolean existsByOrderIdAndStatus(UUID orderId, PaymentStatus status);
    Optional<PaymentModel> findByGatewayPaymentId(String gatewayPaymentId);
}
