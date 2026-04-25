package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.item.CreateItemRequestDTO;
import com.italo.catalogy.dto.item.ItemResponseDTO;
import com.italo.catalogy.dto.item.UpdateItemRequestDTO;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.CategoryModel;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.service.ImageService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ItemMapper {
    private final ImageService imageService;

    public ItemMapper(ImageService imageService) {
        this.imageService = imageService;
    }

    public ItemModel createToModel(CreateItemRequestDTO createItemRequestDTO, CatalogModel catalogModel){
        ItemModel itemModel = new ItemModel();
        itemModel.setName(createItemRequestDTO.name());
        itemModel.setAbout(createItemRequestDTO.about());
        itemModel.setPrice(new BigDecimal(createItemRequestDTO.price().toString()));
        itemModel.setStock(createItemRequestDTO.stock());
        itemModel.setCatalog(catalogModel);
        itemModel.setCreatedAt(LocalDateTime.now());
        itemModel.setUpdatedAt(LocalDateTime.now());
        itemModel.setDeleted(false);
        return itemModel;
    }

    public ItemResponseDTO modelToResponse(ItemModel itemModel){
        return new ItemResponseDTO(
                itemModel.getId(),
                itemModel.getCategory()==null? null : itemModel.getCategory().getName(),
                itemModel.getName(),
                itemModel.getAbout(),
                itemModel.getPrice(),
                itemModel.getStock(),
                itemModel.getDeleted(),
                this.imageService.getAssignedUrlImage(itemModel.getImagePath1()),
                itemModel.getImagePath2()==null? null : this.imageService.getAssignedUrlImage(itemModel.getImagePath2()),
                itemModel.getImagePath3()==null? null : this.imageService.getAssignedUrlImage(itemModel.getImagePath3())
        );
    }

    public ItemModel updateToModel(UpdateItemRequestDTO updateItemRequestDTO, ItemModel itemModel){
        itemModel.setName(updateItemRequestDTO.name());
        itemModel.setAbout(updateItemRequestDTO.about());
        itemModel.setPrice(updateItemRequestDTO.price());
        itemModel.setUpdatedAt(LocalDateTime.now());
        return itemModel;

    }
}
