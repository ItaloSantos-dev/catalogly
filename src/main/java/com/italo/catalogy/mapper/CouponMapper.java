package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.coupon.CouponResponseDTO;
import com.italo.catalogy.dto.coupon.CreateCouponRequestDTO;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.CouponModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CouponMapper {
    public CouponModel createToModel(CreateCouponRequestDTO createCouponRequestDTO, CatalogModel catalogModel){
        CouponModel couponModel = new CouponModel();
        couponModel.setSlug(createCouponRequestDTO.slug());
        couponModel.setActive(true);
        couponModel.setCatalog(catalogModel);
        couponModel.setAmount(createCouponRequestDTO.amount());
        couponModel.setAmountMinimum(createCouponRequestDTO.amountMinimum());
        couponModel.setAmountDiscountMaximum(createCouponRequestDTO.amountDiscountMaximum());
        couponModel.setCreatedAt(LocalDateTime.now());
        couponModel.setUpdatedAt(LocalDateTime.now());
        return couponModel;
    }

    public CouponResponseDTO modelToResponse(CouponModel couponModel){
        return new CouponResponseDTO(
                couponModel.getId(),
                couponModel.getSlug(),
                couponModel.getAmount(),
                couponModel.getAmountMinimum(),
                couponModel.getAmountDiscountMaximum()
        );
    }
}
