package com.italo.catalogy.service;

import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.respository.CatalogRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public CatalogModel getCatalogBySlug(String slug){
        return this.catalogRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));
    }
}
