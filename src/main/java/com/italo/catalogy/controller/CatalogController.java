package com.italo.catalogy.controller;

import com.italo.catalogy.dto.catalog.CatalogPrivateResponseDTO;
import com.italo.catalogy.dto.catalog.CatalogPublicResponseDTO;
import com.italo.catalogy.dto.catalog.CreateCatalogRequestDTO;
import com.italo.catalogy.dto.catalog.UpdateCatalogRequestDTO;
import com.italo.catalogy.mapper.CatalogMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;
    private final CatalogMapper catalogMapper;

    public CatalogController(CatalogService catalogService, CatalogMapper catalogMapper) {
        this.catalogService = catalogService;
        this.catalogMapper = catalogMapper;
    }

    @GetMapping("/{slug}")
    public ResponseEntity<CatalogPublicResponseDTO> getCatalogBySlug(@PathVariable String slug){
        CatalogModel catalog = this.catalogService.getCatalogBySlug(slug);
        return ResponseEntity.ok(this.catalogMapper.modelToPublicResponse(catalog));
    }

    @PostMapping
    public ResponseEntity<CatalogPublicResponseDTO> createCatalog(
            @AuthenticationPrincipal UserModel userModel,
            @RequestPart("data") CreateCatalogRequestDTO createCatalogRequestDTO,
            @RequestPart("imageIconUrl") MultipartFile imageIcon,
            @RequestPart("imageBannerUrl") MultipartFile imageBanner
            ){
        CatalogModel catalogModel = this.catalogService.createCatalog(createCatalogRequestDTO, userModel.getId(), imageIcon, imageBanner);
        return ResponseEntity.ok(this.catalogMapper.modelToPublicResponse(catalogModel));
    }

    @PutMapping
    public ResponseEntity<CatalogPublicResponseDTO> updateCatalog(
            @AuthenticationPrincipal UserModel userModel,
            @RequestPart("data") UpdateCatalogRequestDTO updateCatalogRequestDTO,
            @RequestPart("imageIconUrl") MultipartFile imageIcon,
            @RequestPart("imageBannerUrl") MultipartFile imageBanner
    ){
        CatalogModel catalogModel = this.catalogService.updateCatalogBySellerrId(userModel.getId(), updateCatalogRequestDTO, imageIcon, imageBanner);
        return ResponseEntity.ok(this.catalogMapper.modelToPublicResponse(catalogModel));
    }

}
