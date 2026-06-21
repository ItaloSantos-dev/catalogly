package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.catalog.CatalogPrivateResponseDTO;
import com.italo.catalogy.dto.catalog.CatalogPublicResponseDTO;
import com.italo.catalogy.dto.catalog.CreateCatalogRequestDTO;
import com.italo.catalogy.dto.catalog.UpdateCatalogRequestDTO;
import com.italo.catalogy.dto.catalog.dashboard.CatalogDashboard;
import com.italo.catalogy.dto.category.CategoryNameAndId;
import com.italo.catalogy.dto.catalog.dashboard.*;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.ImageService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class CatalogMapper {

    private final ImageService imageService;
    private final SellerMapper sellerMapper;

    public CatalogMapper(ImageService imageService, SellerMapper sellerMapper) {
        this.imageService = imageService;
        this.sellerMapper = sellerMapper;
    }



    public CatalogModel createToModel(CreateCatalogRequestDTO createCatalogRequestDTO, SellerModel sellerModel){
        CatalogModel newCatalogModel = new CatalogModel();
        newCatalogModel.setName(createCatalogRequestDTO.name());
        newCatalogModel.setSlug(createCatalogRequestDTO.slug());
        newCatalogModel.setSlogan(createCatalogRequestDTO.slogan());
        newCatalogModel.setAbout(createCatalogRequestDTO.about());
        newCatalogModel.setFisicAddress(createCatalogRequestDTO.fisicAddress());
        newCatalogModel.setPhone(createCatalogRequestDTO.phone());
        newCatalogModel.setCreatedAt(LocalDateTime.now());
        newCatalogModel.setUpdatedAt(LocalDateTime.now());
        newCatalogModel.setSeller(sellerModel);
        return newCatalogModel;
    }

    public CatalogPublicResponseDTO modelToPublicResponse(CatalogModel catalogModel){
        return new CatalogPublicResponseDTO(
                catalogModel.getSeller().getUser().getFirstName(),
                catalogModel.getName(),
                catalogModel.getSlug(),
                catalogModel.getSlogan(),
                catalogModel.getAbout(),
                catalogModel.getFisicAddress(),
                catalogModel.getPhone(),
                this.imageService.getAssignedUrlImage(catalogModel.getImageIconPath()),
                this.imageService.getAssignedUrlImage(catalogModel.getImageBannerPath())
        );
    }

    public CatalogPrivateResponseDTO modelToPrivateResponse(CatalogModel catalogModel, CatalogDashboard catalogDashboard){
        return new CatalogPrivateResponseDTO(
                catalogModel.getId(),
                catalogModel.getName(),
                catalogModel.getSlug(),
                catalogModel.getSlogan(),
                catalogModel.getAbout(),
                catalogModel.getFisicAddress(),
                catalogModel.getPhone(),
                this.imageService.getAssignedUrlImage(catalogModel.getImageIconPath()),
                this.imageService.getAssignedUrlImage(catalogModel.getImageBannerPath()),
                this.sellerMapper.modelToResponse(catalogModel.getSeller()),
                catalogDashboard,
                catalogModel.getCategorys().stream()
                    .map(category -> new CategoryNameAndId(category.getId(), category.getName()))
                    .toList()
        );
    }

    public CatalogModel updateToModel(CatalogModel catalog, UpdateCatalogRequestDTO updateCatalogRequestDTO){
        catalog.setName(updateCatalogRequestDTO.name());
        catalog.setSlug(updateCatalogRequestDTO.slug());
        catalog.setSlogan(updateCatalogRequestDTO.slogan());
        catalog.setAbout(updateCatalogRequestDTO.about());
        catalog.setFisicAddress(updateCatalogRequestDTO.fisicAddress());
        catalog.setPhone(updateCatalogRequestDTO.phone());
        catalog.setUpdatedAt(LocalDateTime.now());
        return catalog;
    }

    public CatalogDashboard dataToCatalogDashboard(
            UUID catalogId,
            String name,
            String slug,
            Integer totalItems,
            Integer totalCategories,
            Integer totalOrders,
            Integer completedOrders,
            Integer pendingOrders,
            BigDecimal totalRevenue,
            BigDecimal averageOrderValue,
            BigDecimal stockValue,
            Integer lowStockItems,
            Integer outOfStockItems,
            Integer activeCoupons,
            Integer pendingInvoices,
            List<TopItem> topItems,
            List<RecentOrder> recentOrders,
            List<CategorySummary> categories
    ){
        return new CatalogDashboard(
                catalogId,
                name,
                slug,
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
