package com.italo.catalogy.controller;

import com.italo.catalogy.dto.category.CategoryResponseDTO;
import com.italo.catalogy.dto.category.CreateCategoryRequestDTO;
import com.italo.catalogy.mapper.CategoryMapper;
import com.italo.catalogy.model.CategoryModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public CategoryController(CategoryMapper categoryMapper, CategoryService categoryService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CreateCategoryRequestDTO createCategoryRequestDTO, @AuthenticationPrincipal UserModel userModel){
        CategoryModel categoryModel = this.categoryService.createCategory(createCategoryRequestDTO, userModel.getId());
        return ResponseEntity.ok(this.categoryMapper.modelToResponse(categoryModel));
    }
}
