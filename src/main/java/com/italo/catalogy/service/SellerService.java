package com.italo.catalogy.service;

import com.italo.catalogy.dto.seller.CreateSellerRequestDTO;
import com.italo.catalogy.dto.seller.SellerResponseDTO;
import com.italo.catalogy.mapper.SellerMapper;
import com.italo.catalogy.mapper.UserMapper;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.SellerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public SellerService(SellerRepository sellerRepository, SellerMapper sellerMapper, PasswordEncoder passwordEncoder, UserService userService) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public SellerModel createSeller(CreateSellerRequestDTO createSellerRequestDTO, UserModel userModel){
        Boolean tokenIsNull =  userModel == null;

        if (tokenIsNull && createSellerRequestDTO.userData()==null)
            throw new RuntimeException("Deu ruin");

        String emailUser = tokenIsNull ? createSellerRequestDTO.userData().email() : userModel.getEmail();

        if (this.sellerRepository.existsByUserEmail(emailUser))
            throw new RuntimeException("Deu ruin");

        SellerModel newSeller = this.sellerMapper.createToModel(createSellerRequestDTO);

        newSeller.setUser(
                tokenIsNull? this.userService.createUser(createSellerRequestDTO.userData()):
                userModel
        );

        return this.sellerRepository.save(newSeller);

    }
}
