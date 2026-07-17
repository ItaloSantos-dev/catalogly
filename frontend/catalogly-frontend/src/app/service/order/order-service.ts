import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { CreateOrderRequestDTO } from '../../../types/order/create-order-request';
import { Observable } from 'rxjs';
import { OrderResponseDTO } from '../../../types/order/order-response';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private backApi = inject(BackApi);
  
  createOrder(data:CreateOrderRequestDTO):Observable<OrderResponseDTO>{
    return this.backApi.createOrder(data);
  }

  getOrdersOfUser():Observable<OrderResponseDTO[]>{
    return this.backApi.getOrdersOfUser();
  }
}
