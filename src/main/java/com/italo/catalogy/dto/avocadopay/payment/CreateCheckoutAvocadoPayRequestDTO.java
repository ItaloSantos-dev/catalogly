package com.italo.catalogy.dto.avocadopay.payment;

import com.italo.catalogy.model.enums.PaymentMethod;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record CreateCheckoutAvocadoPayRequestDTO(
        List<ItemQuantityAvocadoPay> items,
        List<PaymentMethod> methods,
        String returnUrl,
        String completionUrl,
        String  externalId,
        Card card

) {
}
