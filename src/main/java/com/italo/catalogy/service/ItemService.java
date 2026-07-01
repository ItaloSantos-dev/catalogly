package com.italo.catalogy.service;

import com.italo.catalogy.dto.avocadopay.item.ItemAvocadoPayResponseDTO;
import com.italo.catalogy.dto.item.CreateItemRequestDTO;
import com.italo.catalogy.dto.item.UpdateImageItem;
import com.italo.catalogy.dto.item.UpdateItemRequestDTO;
import com.italo.catalogy.infra.AvocadoPayConfig;
import com.italo.catalogy.mapper.ItemMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.CategoryModel;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.CategoryRepository;
import com.italo.catalogy.respository.ItemRepository;
import com.italo.catalogy.respository.OrderItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final AvocadoPayConfig avocadoPayConfig;

    private final List<String> EXTENSIONS_IMAGE_ACCEPTDES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    private Boolean validateImage(MultipartFile image){
        return this.EXTENSIONS_IMAGE_ACCEPTDES.contains(image.getContentType());
    }

    public ItemService(ItemRepository itemRepository, CatalogRepository catalogRepository, CategoryRepository categoryRepository, ItemMapper itemMapper, ImageService imageService, OrderItemRepository orderItemRepository, AvocadoPayConfig avocadoPayConfig) {
        this.itemRepository = itemRepository;
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.itemMapper = itemMapper;
        this.imageService = imageService;
        this.orderItemRepository = orderItemRepository;
        this.avocadoPayConfig = avocadoPayConfig;
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
        ResponseEntity<ItemAvocadoPayResponseDTO> avocadoPayResponseDTOResponseEntity = this.avocadoPayConfig.createItem(itemModel);
        if (avocadoPayResponseDTOResponseEntity.getStatusCode()!= HttpStatus.OK)
            throw new RuntimeException("Deu ruin");
        itemModel.setGatewayId(avocadoPayResponseDTOResponseEntity.getBody().data().id());
        return this.itemRepository.save(itemModel);
    }

    public void deleteItemById(UUID id, UserModel userModel){
        System.out.println("Veio");
        ItemModel itemModel = this.itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (!itemModel.getCatalog().getSeller().getUser().getId().equals(userModel.getId()))
            throw new RuntimeException("Deu ruin");

         
        itemModel.setDeleted(true);
        this.itemRepository.save(itemModel);
        return;

    }

    private ItemModel updateImage(ItemModel itemModel, int position, String urlPath){
        if (position==0) {
            itemModel.setImagePath1(urlPath);
        }
        if (position==1) {
            itemModel.setImagePath2(urlPath);
        }
        else{
            itemModel.setImagePath3(urlPath);
        }

        return itemModel;
    }

    private void deleteImage(ItemModel itemModel, int position){
        if (position==0) {
            this.imageService.deleteImage(itemModel.getImagePath1());
        }
        else if(position==1 && itemModel.getImagePath2()!=null){
            this.imageService.deleteImage(itemModel.getImagePath2());
        }
        else if(position==2 && itemModel.getImagePath3()!=null){
            this.imageService.deleteImage(itemModel.getImagePath3());
        }
    }

    private ItemModel updateImagesOfItem(UpdateItemRequestDTO updateItemRequestDTO, ItemModel itemModel, List<MultipartFile> images ){
        if (updateItemRequestDTO.updateImages()!= null && updateItemRequestDTO.updateImages().getFirst() && images.getFirst()==null)
            throw new RuntimeException("Deu ruin");

        if (updateItemRequestDTO.updateImages()!=null) {
            for (int i = 0; i < images.size(); i++) {

                if (updateItemRequestDTO.updateImages().get(i)) {
                    this.deleteImage(itemModel, i);

                    if (images.get(i)!=null ) {
                        if (!this.validateImage(images.get(i))) 
                            throw new RuntimeException("Deu ruin");
                        itemModel = this.updateImage(itemModel, i, this.saveImage(images.get(i)));
                    }
                    else{
                        itemModel = this.updateImage(itemModel, i, null);
                    }

                }
            }
        }

        return itemModel;
    }


    public ItemModel updateItemById(
            UUID id,
            UpdateItemRequestDTO updateItemRequestDTO,
            UUID userId,
            List<MultipartFile> images
    ){
        if (!this.itemRepository.existsByNameAndCatalogSellerUserId(updateItemRequestDTO.name(), userId ))
            throw new RuntimeException("Deu ruin");

        ItemModel itemModel = this.itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        CatalogModel catalogModel = this.catalogRepository.findBySellerUserId(userId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        itemModel = this.itemMapper.updateToModel(updateItemRequestDTO, itemModel);

        itemModel = this.updateImagesOfItem(updateItemRequestDTO, itemModel, images);

        if (updateItemRequestDTO.categoryId()!=null){
            CategoryModel categoryModel = this.categoryRepository.findById(updateItemRequestDTO.categoryId())
                    .orElseThrow(() -> new RuntimeException("Deu ruin"));

            if (!categoryModel.getCatalog().getId().equals(catalogModel.getId()))
                throw new RuntimeException("Deu ruin");

            itemModel.setCategory(categoryModel);
        }
        else{
            itemModel.setCategory(null);
        }

        return this.itemRepository.save(itemModel);
    }

    public List<ItemModel> getItemsOfCatalogByUser(UserModel userModel){
        return this.itemRepository.findByCatalogSellerUserId(userModel.getId());
    }


    public ItemModel getItemById(UUID id){
        return this.itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("deu ruin"));
    }

}
