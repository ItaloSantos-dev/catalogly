import { Component, computed, inject, signal } from '@angular/core';
import { CartItemQuantity } from '../../../../../../types/cart/cart-item-quantity';
import { CartService } from '../../../../../service/cart/cart-service';
import { ItemResponseDTO } from '../../../../../../types/item/item-response';
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { HelperService } from '../../../../../service/helper/helper-service';
import { CatalogService } from '../../../../../service/catalog/catalog-service';
import { CreateOrderRequestDTO } from '../../../../../../types/order/create-order-request';
import { ItemQuantity } from '../../../../../../types/order/item-quantity';
import { OrderService } from '../../../../../service/order/order-service';

@Component({
  selector: 'app-user-show-cart',
  imports: [RouterLink],
  templateUrl: './user-show-cart.html',
  styleUrl: './user-show-cart.css',
})
export class UserShowCart {
  cart = signal(<CartItemQuantity[] | null>[]);
  private cartService = inject(CartService);
  cartTotal = signal(<number>(0));
  totalItemsCount = signal(<number>(0));
  atualSlug = signal("");
  private helperService = inject(HelperService);
  couponSlug = signal("");
  private catalogService = inject(CatalogService);
  private catalogId = "";
  private orderService = inject(OrderService);
  private router = inject(Router)

  constructor (private route:ActivatedRoute){};

  ngOnInit(){

    let catalogSlug = this.route.snapshot.paramMap.get('slug');
    if (catalogSlug){
      this.catalogService.getCatalogPublicBySlug(catalogSlug).subscribe({
        next:(data) =>{
          this.catalogId = data.id;
        }
      })
    }


    this.cartService.atualCar$.subscribe((data) =>{
      if (data) {
        this.cart.set(data);
        this.calculateCartTotal();
      }
    })

    this.helperService.atualSlugOfCataloglyShowUser$.subscribe((data) =>{
      this.atualSlug.set(data)
    })
  }

  createOrderRequestDTO():CreateOrderRequestDTO{
    const coupon = this.couponSlug() !== "" ? this.couponSlug() : null;
    const itemsA:ItemQuantity[] = this.cart()?.map((it) => ({
      
        itemId:it.item.id,
        quantity:it.quantity
      
    })) ?? []
    return{
      catalogId:this.catalogId,
      couponSlug:coupon,
      items:itemsA 

    }
  }


  // Valor Total Geral do Carrinho (Calculado automaticamente de forma reativa)
  calculateCartTotal (){
    let newTotal = 0;
    let newItemCount = 0;
    this.cart()?.forEach((it) =>{
      newTotal += it.item.price * it.quantity;
      newItemCount++;
    })

    this.cartTotal.set(newTotal);
    this.totalItemsCount.set(newItemCount);
  }

 

  // Incrementa quantidade respeitando o estoque máximo do produto
  setItemQuantityCar(item:ItemResponseDTO, add:boolean) {
    this.cartService.setItemCar(item, add);
  }


  // Remove completamente o item independente da quantidade
  removeItem(productId: string) {
    this.cart.update(items => {
      if (!items) return [];
      return items.filter(cartItem => cartItem.item.id !== productId);
    });
  }

  // Limpa o carrinho todo
  clearCart() {
    this.cart.set([]);
  }

  ngOnSubmit(){
    this.orderService.createOrder(this.createOrderRequestDTO()).subscribe({
      next:(data) =>{
        window.open(data.paymentUrl, "_blank");
      }
    })
  }

}
