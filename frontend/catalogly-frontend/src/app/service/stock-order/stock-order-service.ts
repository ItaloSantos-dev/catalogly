import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { CreateStockOrderRequestDTO } from '../../../types/stock-order/create-stock-order-request';
import { Observable } from 'rxjs';
import { StockOrderResponseDTO } from '../../../types/stock-order/stock-order-response';
import { SupplierItemWithCprodResponseDTO } from '../../../types/tie-supplier-item/supplier-item-cprod/supplier-item-with-cprod-response';

@Injectable({
  providedIn: 'root',
})
export class StockOrderService {
  private backApi = inject(BackApi);

  createStockOrder(data:CreateStockOrderRequestDTO):Observable<any>{
    return this.backApi.createStockOrder(data);
  }

  getStockOrderById(id:string):Observable<StockOrderResponseDTO>{
    return this.backApi.getstockORderById(id);
  }

  updateInvoiceXmlOfStockOrderById(data:FormData):Observable<SupplierItemWithCprodResponseDTO>{
    return this.backApi.updateInvoiceXmlOfStockOrderById(data);
  }

}
