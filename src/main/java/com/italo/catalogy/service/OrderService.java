package com.italo.catalogy.service;

import com.italo.catalogy.dto.order.CreateOrderRequestDTO;
import com.italo.catalogy.mapper.OrderMapper;
import com.italo.catalogy.model.*;
import com.italo.catalogy.respository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final CatalogRepository catalogRepository;
    private final CouponRepository couponRepository;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository  , OrderItemService orderItemService, CatalogRepository catalogRepository, CouponRepository couponRepository, OrderMapper orderMapper, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.catalogRepository = catalogRepository;
        this.couponRepository = couponRepository;
        this.orderMapper = orderMapper;
        this.paymentService = paymentService;
    }

    public List<OrderModel> getMyOrders(UserModel userModel){
        return orderRepository.findByBuyerId(userModel.getId());
    }

    public List<OrderModel> getOrdersOfCatalogByUserId(UserModel userModel){
        CatalogModel catalogModel = this.catalogRepository.findBySellerUserId(userModel.getId())
            .orElseThrow(() -> new RuntimeException());
        
        return catalogModel.getOrders();
    }

    public OrderModel createOrder(UserModel userModel, CreateOrderRequestDTO createOrderRequestDTO){
        CatalogModel catalogModel = this.catalogRepository.findById(createOrderRequestDTO.catalogId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (catalogModel.getSeller().getUser().getId().equals(userModel.getId()))
            throw new RuntimeException("Deu ruin");
        CouponModel couponModelSelected = null;
        if (createOrderRequestDTO.couponSlug()!=null){
            couponModelSelected = this.couponRepository.findBySlugAndCatalogId(createOrderRequestDTO.couponSlug(), catalogModel.getId())
                    .orElseThrow(() -> new RuntimeException("Deu ruin"));
            //Adicionar regra se o cupon tiver, valor mínimo e máximo

        }
        OrderModel orderModel = this.orderMapper.createToModel(userModel, couponModelSelected, catalogModel);
        orderModel.setItens(this.orderItemService.createListOfOrderItems(createOrderRequestDTO.items(), orderModel));
        BigDecimal priceInitial = BigDecimal.ZERO;

        for (OrderItemModel orderItem : orderModel.getItens()){
            if (!orderItem.getItem().getCatalog().getId().equals(catalogModel.getId())) {
                throw new RuntimeException("Deu ruin");
            }
            priceInitial = priceInitial.add(orderItem.getPriceFinal());
        }

        BigDecimal couponDiscount = couponModelSelected==null? null : couponModelSelected.getAmount();

        BigDecimal priceFinal = couponModelSelected==null?
                priceInitial :
                priceInitial.subtract(priceInitial.multiply(couponDiscount));

        orderModel.setPriceInitial(priceInitial);
        orderModel.setCouponDiscount(couponDiscount);
        orderModel.setPriceFinal(priceFinal.setScale(2, RoundingMode.HALF_UP));

        PaymentModel paymentModel = this.paymentService.createPayment(orderModel);
        orderModel.setPayment(List.of(paymentModel));
        return this.orderRepository.save(orderModel);



    }
}
