package com.italo.catalogy.controller;

import com.italo.catalogy.dto.catalog.CatalogPublicResponseDTO;
import com.italo.catalogy.mapper.CatalogMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
