package com.italo.catalogy.controller;

import com.italo.catalogy.dto.catalog.CatalogPrivateResponseDTO;
import com.italo.catalogy.dto.catalog.CatalogPublicResponseDTO;
import com.italo.catalogy.dto.catalog.CreateCatalogRequestDTO;
import com.italo.catalogy.dto.catalog.UpdateCatalogRequestDTO;
import com.italo.catalogy.dto.category.CategoryResponseDTO;
import com.italo.catalogy.dto.coupon.CouponResponseDTO;
import com.italo.catalogy.dto.item.ItemResponseDTO;
import com.italo.catalogy.dto.order.OrderResponseDTO;
import com.italo.catalogy.dto.seller.SellerResponseDTO;
import com.italo.catalogy.dto.supplier.SupplierResponseDTO;
import com.italo.catalogy.dto.supplier_item.SupplierItemResponseDTO;
import com.italo.catalogy.mapper.CatalogMapper;
import com.italo.catalogy.mapper.CategoryMapper;
import com.italo.catalogy.mapper.CouponMapper;
import com.italo.catalogy.mapper.ItemMapper;
import com.italo.catalogy.mapper.OrderMapper;
import com.italo.catalogy.mapper.SupplierItemMapper;
import com.italo.catalogy.mapper.SupplierMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.CategoryModel;
import com.italo.catalogy.model.CouponModel;
import com.italo.catalogy.model.ItemModel;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.SupplierModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.CatalogService;
import com.italo.catalogy.service.CategoryService;
import com.italo.catalogy.service.CouponService;
import com.italo.catalogy.service.ItemService;
import com.italo.catalogy.service.OrderService;
import com.italo.catalogy.service.SupplierService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;
    private final CatalogMapper catalogMapper;
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final CouponService couponService;
    private final CouponMapper couponMapper;
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;
    private final SupplierItemMapper supplierItemMapper;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public CatalogController(CatalogService catalogService, CatalogMapper catalogMapper, ItemService itemService, ItemMapper itemMapper, CategoryService categoryService, CategoryMapper categoryMapper, CouponService couponService, CouponMapper couponMapper, SupplierService supplierService, SupplierMapper supplierMapper, SupplierItemMapper supplierItemMapper, OrderService orderService, OrderMapper orderMapper) {
        this.itemMapper = itemMapper;
        this.itemService = itemService;
        this.catalogService = catalogService;
        this.catalogMapper = catalogMapper;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.couponMapper = couponMapper;
        this.couponService = couponService;
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
        this.supplierItemMapper = supplierItemMapper;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    private List<SupplierResponseDTO> generatSupplierResponseDTOs(UserModel userModel){
        List<SupplierModel> suppliers = this.supplierService.getSuppliersOfCatalogById(userModel);
        List<SupplierResponseDTO> response = new ArrayList<>();

        for (SupplierModel supplier : suppliers) {
            boolean supplierHasItens = supplier.getItems()!=null && supplier.getItems().size()>0;

            List<SupplierItemResponseDTO> itensOfSupplierResponse = new ArrayList<>();
            
            if (supplierHasItens) {
                itensOfSupplierResponse = supplier.getItems().stream()
                    .map(item -> this.supplierItemMapper.modelToResponse(item))
                    .toList();
            }

            response.add(
                this.supplierMapper.modelToResponse(supplier, itensOfSupplierResponse)
            );

        }

        return response;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersOfCatalogByUserId(@AuthenticationPrincipal UserModel userModel){
        List<OrderModel> orders = this.orderService.getOrdersOfCatalogByUserId(userModel);

        List<OrderResponseDTO> response = orders.stream()
            .map(order -> this.orderMapper.modelToResponse(order, null))
            .toList();
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<CatalogPublicResponseDTO> getCatalogBySlug(@PathVariable String slug){
        CatalogModel catalog = this.catalogService.getCatalogBySlug(slug);
        return ResponseEntity.ok(this.catalogMapper.modelToPublicResponse(catalog));
    }

    @GetMapping("/{id}/categorys")
    public ResponseEntity<List<CategoryResponseDTO>> getCategorysOfCatalogById(@PathVariable UUID id){
        List<CategoryModel> categorys = this.categoryService.getCategorysByCatalogId(id);
        List<CategoryResponseDTO> response = categorys.stream()
            .map(categoryMapper::modelToResponse)
            .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CatalogPublicResponseDTO> createCatalog(
            @AuthenticationPrincipal UserModel userModel,
            @RequestPart("data") CreateCatalogRequestDTO createCatalogRequestDTO,
            @RequestPart("imageIconUrl") MultipartFile imageIcon,
            @RequestPart("imageBannerUrl") MultipartFile imageBanner
            ){
        CatalogModel catalogModel = this.catalogService.createCatalog(createCatalogRequestDTO, userModel.getId(), imageIcon, imageBanner);
        return ResponseEntity.ok(this.catalogMapper.modelToPublicResponse(catalogModel));
    }

    @PutMapping
    public ResponseEntity<CatalogPublicResponseDTO> updateCatalog(
            @AuthenticationPrincipal UserModel userModel,
            @RequestPart("data") UpdateCatalogRequestDTO updateCatalogRequestDTO,
            @RequestPart("imageIconUrl") MultipartFile imageIcon,
            @RequestPart("imageBannerUrl") MultipartFile imageBanner
    ){
        CatalogModel catalogModel = this.catalogService.updateCatalogBySellerrId(userModel.getId(), updateCatalogRequestDTO, imageIcon, imageBanner);
        return ResponseEntity.ok(this.catalogMapper.modelToPublicResponse(catalogModel));
    }


    @GetMapping("/items")
    public ResponseEntity<List<ItemResponseDTO>> getItemsOfCatalog(@AuthenticationPrincipal UserModel userModel){
        List<ItemModel> items = this.itemService.getItemsOfCatalogByUser(userModel);
        List<ItemResponseDTO> itemsResponse = items.stream()
            .map(itemMapper::modelToResponse)
            .toList();
        return ResponseEntity.ok(itemsResponse);
    }

    @GetMapping("/suppliers")
    public ResponseEntity<List<SupplierResponseDTO>> getSuppliersOfCatalog(@AuthenticationPrincipal UserModel userModel){
        return ResponseEntity.ok(this.generatSupplierResponseDTOs(userModel));
    }

    @GetMapping("/{id}/coupons")
    public ResponseEntity<List<CouponResponseDTO>> getCouponsOfCatalogBySlug(@PathVariable UUID id){
        List<CouponModel> coupons = this.couponService.getCouponsOfCatalogById(id);
        List<CouponResponseDTO> response = coupons.stream()
            .map(coupon -> this.couponMapper.modelToResponse(coupon))
            .toList();

        return ResponseEntity.ok(response);
    }
}
