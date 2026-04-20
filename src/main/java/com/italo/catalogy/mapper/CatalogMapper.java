package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.catalog.CatalogPublicResponseDTO;
import com.italo.catalogy.dto.catalog.CreateCatalogRequestDTO;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.ImageService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CatalogMapper {

    private final ImageService imageService;

    public CatalogMapper(ImageService imageService) {
        this.imageService = imageService;
    }

    public CatalogModel createToModel(CreateCatalogRequestDTO createCatalogRequestDTO, SellerModel sellerModel){
        CatalogModel newCatalogModel = new CatalogModel();
        newCatalogModel.setName(createCatalogRequestDTO.name());
        newCatalogModel.setSlug(createCatalogRequestDTO.slug());
        newCatalogModel.setSlogan(createCatalogRequestDTO.slogan());
        newCatalogModel.setAbout(createCatalogRequestDTO.about());
        newCatalogModel.setFisicAddress(createCatalogRequestDTO.fisicAddress());
        newCatalogModel.setPhone(createCatalogRequestDTO.phone());
        newCatalogModel.setCreatedAt(LocalDateTime.now());
        newCatalogModel.setUpdatedAt(LocalDateTime.now());
        newCatalogModel.setSeller(sellerModel);
        return newCatalogModel;
    }

    public CatalogPublicResponseDTO modelToPublicResponse(CatalogModel catalogModel){
        return new CatalogPublicResponseDTO(
                catalogModel.getSeller().getUser().getFirstName(),
                catalogModel.getName(),
                catalogModel.getSlug(),
                catalogModel.getSlogan(),
                catalogModel.getAbout(),
                catalogModel.getFisicAddress(),
                catalogModel.getPhone(),
                this.imageService.getAssignedUrlImage(catalogModel.getImageIconPath()),
                this.imageService.getAssignedUrlImage(catalogModel.getImageBannerPath())
        );
    }
}
