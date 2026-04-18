package com.italo.catalogy.dto.seller;

import com.italo.catalogy.dto.auth.RegisterRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.jspecify.annotations.Nullable;

public record CreateSellerRequestDTO (
        @Nullable
        RegisterRequestDTO userData,

        @CPF
        String cpf,

        @NotBlank
        @Size(min = 11, max = 11)
        String phone


)
{
}
