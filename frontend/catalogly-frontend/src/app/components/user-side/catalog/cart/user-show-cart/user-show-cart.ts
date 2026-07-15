import { Component, computed, inject, signal } from '@angular/core';
import { CartItemQuantity } from '../../../../../../types/cart/cart-item-quantity';
import { CartService } from '../../../../../service/cart/cart-service';
import { ItemResponseDTO } from '../../../../../../types/item/item-response';

@Component({
  selector: 'app-user-show-cart',
  imports: [],
  templateUrl: './user-show-cart.html',
  styleUrl: './user-show-cart.css',
})
export class UserShowCart {
  cart = signal(<CartItemQuantity[] | null>[]);
  private cartService = inject(CartService);
  cartTotal = signal(<number>(0));
  totalItemsCount = signal(<number>(0));

  ngOnInit(){
    this.cartService.atualCar$.subscribe((data) =>{
      if (data) {
        this.cart.set(data);
        this.calculateCartTotal();
      }
    })
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

}
