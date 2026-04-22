package com.italo.catalogy.controller;

import com.italo.catalogy.dto.item.CreateItemRequestDTO;
import com.italo.catalogy.dto.item.ItemResponseDTO;
import com.italo.catalogy.mapper.ItemMapper;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.ItemService;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("item")
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;


    public ItemController(ItemService itemService, ItemMapper itemMapper) {

        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(
            @AuthenticationPrincipal UserModel userModel,
            @RequestPart("data")CreateItemRequestDTO createItemRequestDTO,
            @RequestPart("image1") @NotNull MultipartFile image1,
            @RequestPart("image2") @Nullable MultipartFile image2,
            @RequestPart("image3") @Nullable MultipartFile image3
            ){
        ItemModel itemModel = this.itemService.createItem(createItemRequestDTO, userModel.getId(), image1, image2, image3);
        return ResponseEntity.ok(this.itemMapper.modelToResponse(itemModel));
    }
}
