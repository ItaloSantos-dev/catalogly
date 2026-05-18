package com.italo.catalogy.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italo.catalogy.dto.avocadopay.payment.CheckoutAvocadoRespondeDTO;
import com.italo.catalogy.dto.avocadopay.payment.CreateCheckoutAvocadoPayRequestDTO;
import com.italo.catalogy.dto.avocadopay.webhook.WebhookRequestBodyDTO;
import com.italo.catalogy.infra.AvocadoPayConfig;
import com.italo.catalogy.mapper.PaymentMapper;
import com.italo.catalogy.model.InvoiceModel;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.PaymentModel;
import com.italo.catalogy.model.enums.PaymentStatus;
import com.italo.catalogy.respository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AvocadoPayConfig avocadoPayConfig;
    private final PaymentMapper paymentMapper;
    private final InvoiceService invoiceService;

    private final BigDecimal percentAmountForSeller = new BigDecimal("0.95");


    public PaymentService(PaymentRepository paymentRepository, AvocadoPayConfig avocadoPayConfig, PaymentMapper paymentMapper, InvoiceService invoiceService) {
        this.paymentRepository = paymentRepository;
        this.avocadoPayConfig = avocadoPayConfig;
        this.paymentMapper = paymentMapper;
        this.invoiceService = invoiceService;
    }

    public PaymentModel createPayment(OrderModel orderModel){
        if (this.paymentRepository.existsByOrderIdAndStatus(orderModel.getId(), PaymentStatus.PAID))
            throw new RuntimeException("Deu ruin");
        CreateCheckoutAvocadoPayRequestDTO createCheckoutAvocadoPayRequestDTO = this.paymentMapper.orderToCreateChechout(orderModel);

        ResponseEntity<CheckoutAvocadoRespondeDTO> checkoutAvocadoRespondeDTOResponseEntity = this.avocadoPayConfig.createCheckout(createCheckoutAvocadoPayRequestDTO);

        if (checkoutAvocadoRespondeDTOResponseEntity.getStatusCode()!= HttpStatus.OK)
            throw new RuntimeException("Deu ruin");

        PaymentModel paymentModel = this.paymentMapper.responseCheckoutToPaymentModel(checkoutAvocadoRespondeDTOResponseEntity.getBody(), orderModel, this.avocadoPayConfig.getGatewayName());

        return paymentModel;
    }

    private WebhookRequestBodyDTO validateRequestWebhook(String gatewayWebhookSecret, String webhookRequestRaw, String signature){
        if (!gatewayWebhookSecret.equals(this.avocadoPayConfig.getGatewayWebhookSecret()))
            throw new RuntimeException("Deu ruin");

        try{
            //pego o hmac
            Mac mac = Mac.getInstance("HmacSHA256");
            //Crio o secret do meu Hmac
            SecretKeySpec secretKey = new SecretKeySpec(AvocadoPayConfig.getABACATEPAY_PUBLIC_KEY().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            //Inicio o Hmac para que a assinatura seja gerada com a key publica do abacate pay
            mac.init(secretKey);
            //Gero o hash, usando o Hmac registrado com a chave api, do body raw do request
            byte[] hash = mac.doFinal(webhookRequestRaw.getBytes(StandardCharsets.UTF_8));
            //Passo de byte para string
            String finalSignature = Base64.getEncoder().encodeToString(hash);
            if (!finalSignature.equals(signature))
                throw new RuntimeException("Deu ruin");

        }catch (NoSuchAlgorithmException | InvalidKeyException exception  ){
            throw new RuntimeException("Deu ruin");
        }
        WebhookRequestBodyDTO webhookRequestBodyDTO;
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            webhookRequestBodyDTO = mapper.readValue(webhookRequestRaw, WebhookRequestBodyDTO.class);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }

        return  webhookRequestBodyDTO;
    }

    public void updateStatusPayment(String gatewayWebhookSecret, String webhookRequestRaw, String signature){
        WebhookRequestBodyDTO webhookRequestBodyDTO = this.validateRequestWebhook(gatewayWebhookSecret, webhookRequestRaw, signature);
        PaymentModel paymentModel = this.paymentRepository.findByGatewayPaymentId(webhookRequestBodyDTO.data().checkout().id())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));
        paymentModel = this.paymentMapper.requestWebhookToUpdatePayment(webhookRequestBodyDTO, paymentModel, this.percentAmountForSeller);
        if (paymentModel.getStatus()==PaymentStatus.PAID){
            InvoiceModel invoiceModel = this.invoiceService.createInvoices(paymentModel);
            paymentModel.setInvoice(invoiceModel);
        }
        this.paymentRepository.save(paymentModel);

    }

}
