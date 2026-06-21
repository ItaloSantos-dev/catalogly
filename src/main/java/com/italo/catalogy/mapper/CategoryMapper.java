package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.category.CategoryResponseDTO;
import com.italo.catalogy.dto.category.CreateCategoryRequestDTO;
import com.italo.catalogy.dto.category.UpdateCategoryRequestDTO;
import com.italo.catalogy.model.CategoryModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CategoryMapper {
    public CategoryModel createToModel(CreateCategoryRequestDTO createCategoryRequestDTO, UUID id){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(id);
        categoryModel.setName(createCategoryRequestDTO.name());
        categoryModel.setDescription(createCategoryRequestDTO.description());
        categoryModel.setCreatedAt(LocalDateTime.now());
        categoryModel.setUpdatedAt(LocalDateTime.now());
        return categoryModel;
    }

    public CategoryResponseDTO modelToResponse(CategoryModel categoryModel){
        Integer itemsCount = categoryModel.getItems()==null? 0: categoryModel.getItems().size();
        return new CategoryResponseDTO(
                categoryModel.getId(),
                categoryModel.getName(),
                categoryModel.getDescription(),
                itemsCount
        );
    }

    public CategoryModel updateToModel(CategoryModel categoryModel, UpdateCategoryRequestDTO updateCategoryRequestDTO){
        categoryModel.setName(updateCategoryRequestDTO.name());
        categoryModel.setDescription(updateCategoryRequestDTO.description());
        return categoryModel;
    }
}
