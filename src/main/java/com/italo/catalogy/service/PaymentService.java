package com.italo.catalogy.service;

import com.italo.catalogy.dto.avocadopay.payment.CheckoutAvocadoRespondeDTO;
import com.italo.catalogy.dto.avocadopay.payment.CreateCheckoutAvocadoPayRequestDTO;
import com.italo.catalogy.infra.AvocadoPayConfig;
import com.italo.catalogy.mapper.PaymentMapper;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.PaymentModel;
import com.italo.catalogy.model.enums.PaymentStatus;
import com.italo.catalogy.respository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AvocadoPayConfig avocadoPayConfig;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, AvocadoPayConfig avocadoPayConfig, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.avocadoPayConfig = avocadoPayConfig;
        this.paymentMapper = paymentMapper;
    }

    public PaymentModel createPayment(OrderModel orderModel){
        if (this.paymentRepository.existsByOrderIdAndStatus(orderModel.getId(), PaymentStatus.APPROVED))
            throw new RuntimeException("Deu ruin");
        CreateCheckoutAvocadoPayRequestDTO createCheckoutAvocadoPayRequestDTO = this.paymentMapper.orderToCreateChechout(orderModel);

        ResponseEntity<CheckoutAvocadoRespondeDTO> checkoutAvocadoRespondeDTOResponseEntity = this.avocadoPayConfig.createCheckout(createCheckoutAvocadoPayRequestDTO);

        if (checkoutAvocadoRespondeDTOResponseEntity.getStatusCode()!= HttpStatus.OK)
            throw new RuntimeException("Deu ruin");

        PaymentModel paymentModel = this.paymentMapper.responseCheckoutToPaymentModel(checkoutAvocadoRespondeDTOResponseEntity.getBody(), orderModel, this.avocadoPayConfig.getGatewayName());

        return paymentModel;
    }

}
