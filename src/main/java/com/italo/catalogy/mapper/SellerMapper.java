package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.seller.CreateSellerRequestDTO;
import com.italo.catalogy.dto.seller.SellerResponseDTO;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.model.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SellerMapper {
    public SellerModel createToModel(CreateSellerRequestDTO createSellerRequestDTO){
        SellerModel newSeller = new SellerModel();
        newSeller.setPhone(createSellerRequestDTO.phone());
        newSeller.setCpf(createSellerRequestDTO.cpf());
        newSeller.setCreatedAt(LocalDateTime.now());
        newSeller.setUpdatedAt(LocalDateTime.now());
        return  newSeller;

    }



    public SellerResponseDTO modelToResponse (SellerModel sellerModel){
        return new SellerResponseDTO(sellerModel.getUser().getFirstName(), sellerModel.getUser().getLastName(), sellerModel.getUser().getRole(), sellerModel.getPhone());
    }
}
