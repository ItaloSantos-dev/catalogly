package com.italo.catalogy.service;

import com.italo.catalogy.dto.category.CreateCategoryRequestDTO;
import com.italo.catalogy.mapper.CategoryMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.CategoryModel;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CatalogRepository catalogRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CatalogRepository catalogRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.catalogRepository = catalogRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryModel createCategory(CreateCategoryRequestDTO createCategoryRequestDTO, UUID userId){
        CatalogModel catalogModel = this.catalogRepository.findBySellerUserId(userId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        CategoryModel newCategory = this.categoryMapper.createToModel(createCategoryRequestDTO);

        if (createCategoryRequestDTO.items()!=null && !createCategoryRequestDTO.items().isEmpty()){
            //Relacionar items com a categoria
        }
        newCategory.setCatalog(catalogModel);
        return this.categoryRepository.save(newCategory);
    }
}
