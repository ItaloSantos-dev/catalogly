package com.italo.catalogy.service;

import com.italo.catalogy.dto.catalog.CatalogPublicResponseDTO;
import com.italo.catalogy.dto.catalog.CreateCatalogRequestDTO;
import com.italo.catalogy.mapper.CatalogMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.model.enums.TypeImageCatalog;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.SellerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CatalogService {
    private final List<String> EXTENSIONS_IMAGE_ACCEPTDES = Arrays.asList("image/jpeg", "image/png", "image/gif");

    private final CatalogRepository catalogRepository;
    private final SellerRepository sellerRepository;
    private final CatalogMapper catalogMapper;
    private final ImageService imageService;

    public CatalogService(CatalogRepository catalogRepository, SellerRepository sellerRepository, CatalogMapper catalogMapper, ImageService imageService) {
        this.catalogRepository = catalogRepository;
        this.sellerRepository = sellerRepository;
        this.catalogMapper = catalogMapper;
        this.imageService = imageService;
    }

    public CatalogModel getCatalogBySlug(String slug){
        return this.catalogRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));
    }

    private Boolean validateImage(MultipartFile image){
        return this.EXTENSIONS_IMAGE_ACCEPTDES.contains(image.getContentType());
    }

    // /catalogly-media/catalog/{id}/banner|icon/{UUID}.extensao
    private String saveImage(MultipartFile image, TypeImageCatalog type){
        UUID objectId = UUID.randomUUID();
        String extension = image.getContentType().split("/")[1];
        String path = "/catalog/" + type.toString().toLowerCase() + "/" + objectId + "." + extension;
        this.imageService.uploadImage(image, path);
        return path;
    }

    private SellerModel validateDateOfCatalog(String catalogSlug, String catalogName,UserModel userModel, MultipartFile imageIcon, MultipartFile imageBanner){
        if (!validateImage(imageIcon) || !validateImage(imageBanner))
            throw new RuntimeException("Deu ruin");

        if (this.catalogRepository.existsBySlug(catalogSlug))
            throw new RuntimeException("Deu ruin");

        if (this.catalogRepository.existsByNameAndSellerUserId(catalogName, userModel.getId()))
            throw new RuntimeException("Deu ruin");

        SellerModel seller = this.sellerRepository.findByUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (seller.getCatalog()!=null)
            throw new RuntimeException("Deu ruin");

        return seller;
    }

    public CatalogModel createCatalog(CreateCatalogRequestDTO createCatalogRequestDTO, UserModel userModel, MultipartFile imageIcon, MultipartFile imageBanner){

        SellerModel seller = this.validateDateOfCatalog(
                createCatalogRequestDTO.slug(),
                createCatalogRequestDTO.name(),
                userModel, imageIcon, imageBanner
        );

        CatalogModel catalogModel = this.catalogMapper.createToModel(createCatalogRequestDTO, seller);
        catalogModel.setImageIconPath(this.saveImage(imageIcon, TypeImageCatalog.ICON));
        catalogModel.setImageBannerPath(this.saveImage(imageBanner, TypeImageCatalog.BANNER));
        return this.catalogRepository.save(catalogModel);

    }

    public CatalogModel getBySellerId(UUID id){
        return this.catalogRepository.findBySellerUserId(id)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));
    }
}
