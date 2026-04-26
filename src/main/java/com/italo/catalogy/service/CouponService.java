package com.italo.catalogy.service;

import com.italo.catalogy.dto.coupon.CreateCouponRequestDTO;
import com.italo.catalogy.mapper.CouponMapper;
import com.italo.catalogy.model.CatalogModel;
import com.italo.catalogy.model.CouponModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.CatalogRepository;
import com.italo.catalogy.respository.CouponRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CatalogRepository catalogRepository;
    private final CouponMapper couponMapper;

    public CouponService(CouponRepository couponRepository, CatalogRepository catalogRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.catalogRepository = catalogRepository;
        this.couponMapper = couponMapper;
    }

    private void validateDataCoupon(){

    }

    public CouponModel createCoupon(UserModel userModel, CreateCouponRequestDTO createCouponRequestDTO){
        CatalogModel catalogModel = this.catalogRepository.findBySellerUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (this.couponRepository.existsBySlugAndCatalogId(createCouponRequestDTO.slug(), catalogModel.getId()))
            throw new RuntimeException("Deu ruin");
        if (createCouponRequestDTO.amount().compareTo(BigDecimal.ZERO)<0)
            throw new RuntimeException("Deu ruin");

        CouponModel couponModel = this.couponMapper.createToModel(createCouponRequestDTO, catalogModel);
        return this.couponRepository.save(couponModel);
    }
}
