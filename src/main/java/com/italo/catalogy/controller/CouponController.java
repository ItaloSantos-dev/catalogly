package com.italo.catalogy.controller;

import com.italo.catalogy.dto.coupon.CouponResponseDTO;
import com.italo.catalogy.dto.coupon.CreateCouponRequestDTO;
import com.italo.catalogy.dto.coupon.UpdateCouponRequestDTO;
import com.italo.catalogy.mapper.CouponMapper;
import com.italo.catalogy.model.CouponModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("coupon")
public class CouponController {
    private final CouponService couponService;
    private final CouponMapper couponMapper;


    public CouponController(CouponService couponService, CouponMapper couponMapper) {
        this.couponService = couponService;
        this.couponMapper = couponMapper;
    }

    @PostMapping
    public ResponseEntity<CouponResponseDTO> createCoupon(
            @AuthenticationPrincipal UserModel userModel,
            @RequestBody CreateCouponRequestDTO createCouponRequestDTO
            ){
        CouponModel couponModel = this.couponService.createCoupon(userModel, createCouponRequestDTO);
        return ResponseEntity.ok(this.couponMapper.modelToResponse(couponModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> updateCouponById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserModel userModel,
            @RequestBody UpdateCouponRequestDTO updateCouponRequestDTO
            ){
        CouponModel couponModel = this.couponService.updateCouponById(id, userModel, updateCouponRequestDTO);
        return ResponseEntity.ok(this.couponMapper.modelToResponse(couponModel));
    }
}
