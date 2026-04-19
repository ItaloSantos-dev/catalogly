package com.italo.catalogy.service;

import com.italo.catalogy.dto.catalog.CatalogPublicResponseDTO;
import com.italo.catalogy.dto.catalog.CreateCatalogRequestDTO;
import com.italo.catalogy.mapper.CatalogMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.SellerRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final SellerRepository sellerRepository;
    private final CatalogMapper catalogMapper;

    public CatalogService(CatalogRepository catalogRepository, SellerRepository sellerRepository, CatalogMapper catalogMapper) {
        this.catalogRepository = catalogRepository;
        this.sellerRepository = sellerRepository;
        this.catalogMapper = catalogMapper;
    }

    public CatalogModel getCatalogBySlug(String slug){
        return this.catalogRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));
    }

    public CatalogModel createCatalog(CreateCatalogRequestDTO createCatalogRequestDTO, UserModel userModel){
        if (this.catalogRepository.existsBySlug(createCatalogRequestDTO.slug()))
            throw new RuntimeException("Deu ruin");

        SellerModel seller = this.sellerRepository.findByUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (seller.getCatalog()!=null)
            throw new RuntimeException("Deu ruin");

        CatalogModel catalogModel = this.catalogMapper.createToModel(createCatalogRequestDTO, seller);

        //Adicionar tratamento de imagem com minio
        catalogModel.setImageIconPath("Caminho minio");
        catalogModel.setImageBannerPath("Caminho minio");
        return this.catalogRepository.save(catalogModel);


    }
}
