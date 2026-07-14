import { Injectable, signal } from '@angular/core';
import { BehaviorSubject, map } from 'rxjs';
import { CartItemQuantity } from '../../../types/cart/cart-item-quantity';
import { ItemResponseDTO } from '../../../types/item/item-response';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private car = signal(<CartItemQuantity[]>[]);

  private atualCar = new BehaviorSubject<CartItemQuantity[]|null>(null);

  atualCar$ = this.atualCar.asObservable();



  setItemCar(item:ItemResponseDTO, add:boolean){
    let itemHasExists = false;
    let newCar = this.car();
    let removeItem;

    newCar.forEach((item, index)=>{
      if (item.item.id===item.item.id) {

        itemHasExists = true;
        if (add) 
          item.quantity +=1;
        else
          item.quantity-=1;
        if (item.quantity===0) {
          removeItem = index;
        }
      }
    })

    if (!itemHasExists) {
      newCar.push({item:item, quantity:1})
    }

    if (removeItem) {
      newCar.slice(removeItem,1);
    }

    this.atualCar.next(newCar)
    console.log(this.car());
    
  }
}
