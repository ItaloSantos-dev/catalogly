package com.italo.catalogy.service;

import com.italo.catalogy.dto.item.CreateItemRequestDTO;
import com.italo.catalogy.mapper.ItemMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.CategoryModel;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.CategoryRepository;
import com.italo.catalogy.respository.ItemRepository;
import com.italo.catalogy.respository.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;
    private final ItemMapper itemMapper;
    private final ImageService imageService;
    private final OrderItemRepository orderItemRepository;

    private final List<String> EXTENSIONS_IMAGE_ACCEPTDES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    private Boolean validateImage(MultipartFile image){
        return this.EXTENSIONS_IMAGE_ACCEPTDES.contains(image.getContentType());
    }

    public ItemService(ItemRepository itemRepository, CatalogRepository catalogRepository, CategoryRepository categoryRepository, ItemMapper itemMapper, ImageService imageService, OrderItemRepository orderItemRepository) {
        this.itemRepository = itemRepository;
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.itemMapper = itemMapper;
        this.imageService = imageService;
        this.orderItemRepository = orderItemRepository;
    }

    private String saveImage(MultipartFile image){
        UUID objectId = UUID.randomUUID();
        String extension = image.getContentType().split("/")[1];
        String path = "/catalog/items/"  + objectId + "." + extension;
        this.imageService.uploadImage(image, path);
        return path;
    }

    private void validateDateOfItem(String itemName, UUID userId, MultipartFile image1){
        if (this.itemRepository.existsByNameAndCatalogSellerUserId(itemName, userId ))
            throw new RuntimeException("Deu ruin");

        if (image1==null)
            throw new RuntimeException("Deu ruin");

        if (!this.validateImage(image1) )
            throw new RuntimeException("Deu ruin");
    }

    public ItemModel createItem(
            CreateItemRequestDTO createItemRequestDTO,
            UUID userId,
            MultipartFile image1,
            MultipartFile image2,
            MultipartFile image3
    ){

        this.validateDateOfItem(createItemRequestDTO.name(), userId, image1);

        CatalogModel catalogModel = this.catalogRepository.findBySellerUserId(userId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        ItemModel itemModel = this.itemMapper.createToModel(createItemRequestDTO, catalogModel);

        itemModel.setImagePath1(this.saveImage(image1));

        if (image2!=null && this.validateImage(image2))
            itemModel.setImagePath2(this.saveImage(image2));

        if (image3!=null && this.validateImage(image3))
            itemModel.setImagePath1(this.saveImage(image3));

        if (createItemRequestDTO.categoryId()!=null){
            CategoryModel categoryModel = this.categoryRepository.findById(createItemRequestDTO.categoryId())
                    .orElseThrow(() -> new RuntimeException("Deu ruin"));

            if (!categoryModel.getCatalog().getId().equals(catalogModel.getId()))
                throw new RuntimeException("Deu ruin");

            itemModel.setCategory(categoryModel);
        }

        return this.itemRepository.save(itemModel);
    }

    public void deleteItemById(UUID id, UserModel userModel){
        ItemModel itemModel = this.itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (!itemModel.getCatalog().getSeller().getUser().getId().equals(userModel.getId()))
            throw new RuntimeException("Deu ruin");

        if (this.orderItemRepository.existsByItemId(id)){
            itemModel.setDeleted(true);
            this.itemRepository.save(itemModel);
            return;
        }
        this.itemRepository.deleteById(id);
    }



}
