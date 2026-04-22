package com.italo.catalogy.service;

import com.italo.catalogy.dto.seller.CreateSellerRequestDTO;
import com.italo.catalogy.dto.seller.SellerResponseDTO;
import com.italo.catalogy.mapper.SellerMapper;
import com.italo.catalogy.mapper.UserMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.SellerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CatalogRepository catalogRepository;

    public SellerService(SellerRepository sellerRepository, SellerMapper sellerMapper, PasswordEncoder passwordEncoder, UserService userService, CatalogRepository catalogRepository) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.catalogRepository = catalogRepository;
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

    @Transactional
    public void deleteCatalogBySellerId(UUID sellerId){
        SellerModel sellerModel = this.sellerRepository.findByUserId(sellerId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (sellerModel.getCatalog()==null)
            throw  new RuntimeException("Deu ruin");

        CatalogModel catalogModel = sellerModel.getCatalog();

        sellerModel.setCatalog(null);
        this.sellerRepository.save(sellerModel);
        this.catalogRepository.deleteById(catalogModel.getId());
    }
}
