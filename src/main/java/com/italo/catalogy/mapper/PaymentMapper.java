package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.avocadopay.payment.Card;
import com.italo.catalogy.dto.avocadopay.payment.CheckoutAvocadoRespondeDTO;
import com.italo.catalogy.dto.avocadopay.payment.CreateCheckoutAvocadoPayRequestDTO;
import com.italo.catalogy.dto.avocadopay.payment.ItemQuantityAvocadoPay;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.PaymentModel;
import com.italo.catalogy.model.enums.PaymentMethod;
import com.italo.catalogy.model.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PaymentMapper {
    public CreateCheckoutAvocadoPayRequestDTO orderToCreateChechout(OrderModel orderModel){
        List<ItemQuantityAvocadoPay> items = orderModel.getItens().stream()
                .map(itemOrder -> new ItemQuantityAvocadoPay(itemOrder.getItem().getGatewayId(), itemOrder.getAmount()))
                .toList();
        return new CreateCheckoutAvocadoPayRequestDTO(
                items,
                List.of(PaymentMethod.PIX, PaymentMethod.CARD),
                "https://www.youtube.com/", //urlFrontHome
                "https://www.youtube.com/", //urlFrontOrders
                UUID.randomUUID().toString(),
                new Card(6)

        );
    }

    public PaymentModel responseCheckoutToPaymentModel(CheckoutAvocadoRespondeDTO checkoutAvocadoRespondeDTO, OrderModel orderModel, String gateway){
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setId(UUID.fromString(checkoutAvocadoRespondeDTO.data().externalId()));
        paymentModel.setGatewayPaymentId(checkoutAvocadoRespondeDTO.data().id());
        paymentModel.setAmountInitial(orderModel.getPriceInitial());
        paymentModel.setOrder(orderModel);
        paymentModel.setGateway(gateway);
        paymentModel.setStatus(PaymentStatus.PENDING);
        paymentModel.setCreatedAt(LocalDateTime.now());
        paymentModel.setUpdatedAt(LocalDateTime.now());
        paymentModel.setPaymentLink(checkoutAvocadoRespondeDTO.data().url());
        return paymentModel;

    }
}
