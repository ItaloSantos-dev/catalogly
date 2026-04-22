package com.italo.catalogy.service;

import com.italo.catalogy.dto.category.CreateCategoryRequestDTO;
import com.italo.catalogy.mapper.CategoryMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.CategoryModel;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.CategoryRepository;
import com.italo.catalogy.respository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CatalogRepository catalogRepository;
    private final CategoryMapper categoryMapper;
    private final ItemRepository itemRepository;

    public CategoryService(CategoryRepository categoryRepository, CatalogRepository catalogRepository, CategoryMapper categoryMapper, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.catalogRepository = catalogRepository;
        this.categoryMapper = categoryMapper;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public CategoryModel createCategory(CreateCategoryRequestDTO createCategoryRequestDTO, UUID userId){
        CatalogModel catalogModel = this.catalogRepository.findBySellerUserId(userId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        CategoryModel newCategory = this.categoryMapper.createToModel(createCategoryRequestDTO);

        if (createCategoryRequestDTO.items()!=null && !createCategoryRequestDTO.items().isEmpty()){
            List<ItemModel> items =  this.itemRepository.findAllById(createCategoryRequestDTO.items());
            newCategory.setItems(items);
        }
        newCategory.setCatalog(catalogModel);
        return this.categoryRepository.save(newCategory);
    }
}
