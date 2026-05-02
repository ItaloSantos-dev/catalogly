package com.italo.catalogy.dto.avocadopay.item;

public record ItemAvocadoPayResponseDTO(
        ItemDataDTO data,
        String error,
        boolean success
) {
}
