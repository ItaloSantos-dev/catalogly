package com.italo.catalogy.controller;

import com.italo.catalogy.dto.category.CategoryResponseDTO;
import com.italo.catalogy.dto.category.CreateCategoryRequestDTO;
import com.italo.catalogy.dto.category.UpdateCategoryRequestDTO;
import com.italo.catalogy.dto.item.ItemResponseDTO;
import com.italo.catalogy.mapper.CategoryMapper;
import com.italo.catalogy.mapper.ItemMapper;
import com.italo.catalogy.model.CategoryModel;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;
    private final ItemMapper itemMapper;

    public CategoryController(CategoryMapper categoryMapper, CategoryService categoryService, ItemMapper itemMapper) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
        this.itemMapper = itemMapper;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CreateCategoryRequestDTO createCategoryRequestDTO, @AuthenticationPrincipal UserModel userModel){
        CategoryModel categoryModel = this.categoryService.createCategory(createCategoryRequestDTO, userModel.getId());
        return ResponseEntity.ok(this.categoryMapper.modelToResponse(categoryModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@AuthenticationPrincipal UserModel userModel ,@PathVariable UUID id){
        this.categoryService.deleteCategoryById(userModel, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getItensOfCategoryById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserModel userModel,
            @RequestBody UpdateCategoryRequestDTO updateCategoryRequestDTO
            ){
        CategoryModel category = this.categoryService.updateCategoryById(updateCategoryRequestDTO, id, userModel);
        return ResponseEntity.ok(this.categoryMapper.modelToResponse(category));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemResponseDTO>> getItensOfCategoryById(
            @PathVariable UUID id
    ){
        List<ItemModel> items = this.categoryService.getItensOfCategoryById(id);
        List<ItemResponseDTO> response = items.stream()
                .map(this.itemMapper::modelToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
