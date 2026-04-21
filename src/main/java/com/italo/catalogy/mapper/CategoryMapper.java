package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.category.CategoryResponseDTO;
import com.italo.catalogy.dto.category.CreateCategoryRequestDTO;
import com.italo.catalogy.model.CategoryModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper {
    public CategoryModel createToModel(CreateCategoryRequestDTO createCategoryRequestDTO){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(createCategoryRequestDTO.name());
        categoryModel.setDescription(createCategoryRequestDTO.description());
        categoryModel.setCreatedAt(LocalDateTime.now());
        categoryModel.setUpdatedAt(LocalDateTime.now());
        return categoryModel;
    }

    public CategoryResponseDTO modelToResponse(CategoryModel categoryModel){
        Integer itemsCount = categoryModel.getItems()==null? 0: categoryModel.getItems().size();
        return new CategoryResponseDTO(
                categoryModel.getName(),
                categoryModel.getDescription(),
                itemsCount
        );
    }
}
