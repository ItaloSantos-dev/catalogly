package com.italo.catalogy.infra;

import com.italo.catalogy.dto.avocadopay.item.CreateItemAvocadoPayRequestDTO;
import com.italo.catalogy.dto.avocadopay.item.ItemAvocadoPayResponseDTO;
import com.italo.catalogy.dto.avocadopay.payment.CheckoutAvocadoRespondeDTO;
import com.italo.catalogy.dto.avocadopay.payment.CreateCheckoutAvocadoPayRequestDTO;
import com.italo.catalogy.model.ItemModel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Configuration
public class AvocadoPayConfig {
    @Value("${abacatepay.api.token}")
    private String avocadoPayTokenApi;

    private final String baseUrl = "https://api.abacatepay.com/v2/";

    private final String currency = "BRL";

    @Getter
    private final String gatewayName = "AbacatePay";

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders setheaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+this.avocadoPayTokenApi);


        return headers;
    }

    public ResponseEntity<ItemAvocadoPayResponseDTO> createItem(ItemModel itemModel){
        String finalUrl = this.baseUrl + "products/create";
        CreateItemAvocadoPayRequestDTO body = new CreateItemAvocadoPayRequestDTO(
                itemModel.getId(),
                itemModel.getName(),
                itemModel.getPrice().multiply(new BigDecimal("100")).intValue(),
                this.currency

        );
        HttpEntity<CreateItemAvocadoPayRequestDTO> request = new HttpEntity<>(body, this.setheaders());
        return this.restTemplate
                .postForEntity(finalUrl,request ,ItemAvocadoPayResponseDTO.class);
    }

    public ResponseEntity<ItemAvocadoPayResponseDTO> deleteItemById(String id){
        String finalUrl = baseUrl + "products/delete?id="+id;
        HttpEntity<Void> request = new HttpEntity<>(null, this.setheaders());

        return this.restTemplate.postForEntity(finalUrl, request, ItemAvocadoPayResponseDTO.class);
    }

    public ResponseEntity<CheckoutAvocadoRespondeDTO> createCheckout(CreateCheckoutAvocadoPayRequestDTO createCheckoutAvocadoPayRequestDTO){
        String finalUrl = this.baseUrl + "checkouts/create";
        HttpEntity<CreateCheckoutAvocadoPayRequestDTO> request = new HttpEntity<>(createCheckoutAvocadoPayRequestDTO, this.setheaders());

        return this.restTemplate.postForEntity(finalUrl, request, CheckoutAvocadoRespondeDTO.class);
    }

}
