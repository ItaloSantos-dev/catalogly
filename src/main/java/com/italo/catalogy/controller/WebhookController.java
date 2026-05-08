package com.italo.catalogy.controller;

import com.italo.catalogy.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("webhook")
public class WebhookController {

    public final PaymentService paymentService;

    public WebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> updatePayment(
            @RequestBody String webhookRequestRaw,
            @RequestParam String webhookSecret,
            @RequestHeader ("X-Webhook-Signature") String signature
    ){
        this.paymentService.updateStatusPayment(webhookSecret, webhookRequestRaw, signature);
        return ResponseEntity.ok().build();
    }
}
