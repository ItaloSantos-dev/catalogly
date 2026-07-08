package com.italo.catalogy.service;

import com.italo.catalogy.dto.catalog.CreateCatalogRequestDTO;
import com.italo.catalogy.dto.catalog.UpdateCatalogRequestDTO;
import com.italo.catalogy.dto.catalog.dashboard.CatalogDashboard;
import com.italo.catalogy.dto.catalog.dashboard.CategorySummary;
import com.italo.catalogy.dto.catalog.dashboard.RecentOrder;
import com.italo.catalogy.dto.catalog.dashboard.TopItem;
import com.italo.catalogy.mapper.CatalogMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.OrderModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.model.enums.InvoiceStatus;
import com.italo.catalogy.model.enums.OrderStatus;
import com.italo.catalogy.model.enums.TypeImageCatalog;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.CategoryRepository;
import com.italo.catalogy.respository.CouponRepository;
import com.italo.catalogy.respository.InvoiceRepository;
import com.italo.catalogy.respository.ItemRepository;
import com.italo.catalogy.respository.OrderItemRepository;
import com.italo.catalogy.respository.OrderRepository;
import com.italo.catalogy.respository.SellerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CatalogService {
    private final List<String> EXTENSIONS_IMAGE_ACCEPTDES = Arrays.asList("image/jpeg", "image/png", "image/gif");

    private final CatalogRepository catalogRepository;
    private final SellerRepository sellerRepository;
    private final CatalogMapper catalogMapper;
    private final ImageService imageService;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final CouponRepository couponRepository;
    private final InvoiceRepository invoiceRepository;
    private final OrderItemRepository orderItemRepository;

    public CatalogService(CatalogRepository catalogRepository, SellerRepository sellerRepository, CatalogMapper catalogMapper, ImageService imageService, OrderRepository orderRepository, ItemRepository itemRepository, CategoryRepository categoryRepository, CouponRepository couponRepository, InvoiceRepository invoiceRepository, OrderItemRepository orderItemRepository) {
        this.catalogRepository = catalogRepository;
        this.sellerRepository = sellerRepository;
        this.catalogMapper = catalogMapper;
        this.imageService = imageService;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.couponRepository = couponRepository;
        this.invoiceRepository = invoiceRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public CatalogModel getCatalogBySlug(String slug){
        return this.catalogRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));
    }

    private Boolean validateImage(MultipartFile image){
        return this.EXTENSIONS_IMAGE_ACCEPTDES.contains(image.getContentType());
    }

    // /catalogly-media/catalog/{id}/banner|icon/{UUID}.extensao
    private String saveImage(MultipartFile image, TypeImageCatalog type){
        UUID objectId = UUID.randomUUID();
        String extension = image.getContentType().split("/")[1];
        String path = "/catalog/" + type.toString().toLowerCase() + "/" + objectId + "." + extension;
        this.imageService.uploadImage(image, path);
        return path;
    }

    private void validateDateOfCatalog(String catalogSlug, MultipartFile imageIcon, MultipartFile imageBanner){
        if (!validateImage(imageIcon) || !validateImage(imageBanner))
            throw new RuntimeException("Deu ruin");

        if (this.catalogRepository.existsBySlug(catalogSlug))
            throw new RuntimeException("Deu ruin");


    }

    public CatalogModel createCatalog(CreateCatalogRequestDTO createCatalogRequestDTO, UUID userId, MultipartFile imageIcon, MultipartFile imageBanner){

        this.validateDateOfCatalog(
                createCatalogRequestDTO.slug(),
                imageIcon, imageBanner
        );

        if (this.catalogRepository.existsByNameAndSellerUserId(createCatalogRequestDTO.name(), userId))
            throw new RuntimeException("Deu ruin");

        SellerModel seller = this.sellerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));


        if (seller.getCatalog()!=null)
            throw new RuntimeException("Deu ruin");

        CatalogModel catalogModel = this.catalogMapper.createToModel(createCatalogRequestDTO, seller);
        catalogModel.setImageIconPath(this.saveImage(imageIcon, TypeImageCatalog.ICON));
        catalogModel.setImageBannerPath(this.saveImage(imageBanner, TypeImageCatalog.BANNER));
        return this.catalogRepository.save(catalogModel);

    }

    public CatalogModel getBySellerId(UUID id){
        System.out.println(id);
        return this.catalogRepository.findBySellerUserId(id)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));
    }

    public CatalogModel updateCatalogBySellerrId(UUID sellerId, UpdateCatalogRequestDTO updateCatalogRequestDTO, MultipartFile imageIcon, MultipartFile imageBanner){
        this.validateDateOfCatalog(
                updateCatalogRequestDTO.slug(),
                imageIcon, imageBanner
        );
        CatalogModel catalogModel = this.catalogRepository.findBySellerUserId(sellerId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));
        catalogModel = this.catalogMapper.updateToModel(catalogModel, updateCatalogRequestDTO);
        catalogModel.setImageIconPath(this.saveImage(imageIcon, TypeImageCatalog.ICON));
        catalogModel.setImageBannerPath(this.saveImage(imageBanner, TypeImageCatalog.BANNER));
        return this.catalogRepository.save(catalogModel);
    }

    public CatalogDashboard generateDashBoard(UserModel userModel){
        CatalogModel catalogModel = this.catalogRepository.findBySellerUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        UUID catalogId = catalogModel.getId();

        // Contagens básicas
        Integer totalItems = this.itemRepository.countByCatalogIdAndDeletedFalse(catalogId);
        Integer totalCategories = this.categoryRepository.countByCatalogId(catalogId);
        Integer totalOrders = this.orderRepository.countByCatalogId(catalogId);

        // Situação de pedidos
        Integer completedOrders = this.orderRepository.countByCatalogIdAndStatus(catalogId, OrderStatus.COMPLETED);
        Integer pendingOrders = totalOrders - completedOrders;

        // Financeiro
        BigDecimal totalRevenue = this.orderRepository.sumPriceFinalByCatalogIdAndStatus(catalogId, OrderStatus.COMPLETED);
        if (totalRevenue == null) totalRevenue = BigDecimal.ZERO;

        BigDecimal averageOrderValue = completedOrders == 0 ? BigDecimal.ZERO :
                totalRevenue.divide(new BigDecimal(completedOrders), 2, RoundingMode.HALF_UP);

        // Estoque
        BigDecimal stockValue = this.itemRepository.sumStockValueByCatalogId(catalogId);
        if (stockValue == null) stockValue = BigDecimal.ZERO;

        Integer lowStockItems = this.itemRepository.countByCatalogIdAndStockLessThanEqualAndDeletedFalse(catalogId, 5);
        Integer outOfStockItems = this.itemRepository.countByCatalogIdAndStockLessThanEqualAndDeletedFalse(catalogId, 0);

        // Promoções / cupons
        Integer activeCoupons = this.couponRepository.countByCatalogIdAndActiveTrue(catalogId);

        // Fiscal / pagamentos
        Integer pendingInvoices = this.invoiceRepository.countByOrderCatalogIdAndStatus(catalogId, InvoiceStatus.CREATED);

        // Top items
        List<TopItem> topItems = this.orderItemRepository.findTopItemsByCatalogId(catalogId).stream()
                .map(row -> new TopItem(
                        UUID.fromString(row[0].toString()),
                        row[1].toString(),
                        ((Number) row[2]).intValue(),
                        new BigDecimal(row[3].toString())
                ))
                .toList();

        // Pedidos recentes (últimos 10)
        List<OrderModel> recentOrderModels = this.orderRepository.findByCatalogIdOrderByCreatedAtDesc(catalogId);
        List<RecentOrder> recentOrders = recentOrderModels.stream()
                .limit(10)
                .map(order -> new RecentOrder(
                        order.getId(),
                        order.getBuyer().getFirstName(),
                        order.getStatus(),
                        order.getPriceFinal(),
                        order.getCreatedAt()
                ))
                .toList();

        // Categorias
        List<CategorySummary> categories = this.categoryRepository.findByCatalogId(catalogId).stream()
                .map(category -> new CategorySummary(
                        category.getName(),
                        category.getItems() == null ? 0 : category.getItems().size()
                ))
                .toList();

        return this.catalogMapper.dataToCatalogDashboard(
                catalogId,
                catalogModel.getName(),
                catalogModel.getSlug(),
                totalItems,
                totalCategories,
                totalOrders,
                completedOrders,
                pendingOrders,
                totalRevenue,
                averageOrderValue,
                stockValue,
                lowStockItems,
                outOfStockItems,
                activeCoupons,
                pendingInvoices,
                topItems,
                recentOrders,
                categories
        );
    }


}
