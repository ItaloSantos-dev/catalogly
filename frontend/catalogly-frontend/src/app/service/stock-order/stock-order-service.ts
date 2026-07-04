import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { CreateStockOrderRequestDTO } from '../../../types/stock-order/create-stock-order-request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StockOrderService {
  private backApi = inject(BackApi);

  createStockOrder(data:CreateStockOrderRequestDTO):Observable<any>{
    return this.backApi.createStockOrder(data);
  }
}
