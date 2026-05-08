package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.avocadopay.payment.Card;
import com.italo.catalogy.dto.avocadopay.payment.CheckoutAvocadoRespondeDTO;
import com.italo.catalogy.dto.avocadopay.payment.CreateCheckoutAvocadoPayRequestDTO;
import com.italo.catalogy.dto.avocadopay.payment.ItemQuantityAvocadoPay;
import com.italo.catalogy.dto.avocadopay.webhook.WebhookRequestBodyDTO;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.PaymentModel;
import com.italo.catalogy.model.enums.PaymentMethod;
import com.italo.catalogy.model.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public PaymentModel requestWebhookToUpdatePayment(WebhookRequestBodyDTO webhookRequestBodyDTO, PaymentModel paymentModel, BigDecimal percentAmountForSeller){

        BigDecimal amountTotal = new BigDecimal(webhookRequestBodyDTO.data().checkout().amount())
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);

        BigDecimal amountGateway =
                new BigDecimal(webhookRequestBodyDTO.data().checkout().platformFee())
                        .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);

        BigDecimal amountSeller = (amountTotal.subtract(amountGateway)).multiply(percentAmountForSeller).setScale(2, RoundingMode.HALF_UP);

        BigDecimal amountComission = (amountTotal.subtract(amountGateway)).subtract(amountSeller).setScale(2, RoundingMode.HALF_UP);

        paymentModel.setMethod(webhookRequestBodyDTO.data().payerInformation().method());

        paymentModel.setAmountTotal(amountTotal);


        paymentModel.setUpdatedAt(LocalDateTime.now());

        paymentModel.setPaidAt(LocalDateTime.ofInstant(webhookRequestBodyDTO.data().checkout().updatedAt(), ZoneOffset.of("-03:00")));

        paymentModel.setStatus(webhookRequestBodyDTO.data().checkout().status());

        paymentModel.setAmountGateway(amountGateway);

        paymentModel.setAmountSeller(amountSeller);

        paymentModel.setAmountCommission(amountComission);

        return paymentModel;
    }
}
