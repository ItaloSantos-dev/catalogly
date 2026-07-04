import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';
import { SupplierResponseDTO } from '../../../types/supplier/supplier-response';
import { CreateSupplierRequestDTO } from '../../../types/supplier/create-supplier-request';
import { SupplierItemResponseDTO } from '../../../types/supplier-item/supplier-item-response';
import { StockOrderResponseDTO } from '../../../types/stock-order/stock-order-response';

@Injectable({
  providedIn: 'root',
})
export class SupplierService {
  private backApi = inject(BackApi);

  createSupplier(data:CreateSupplierRequestDTO):Observable<any>{
    return this.backApi.createSupplier(data);
  }

  updateActiveSupplierById(id:string):Observable<void>{
    return this.backApi.updateActiveSupplierById(id);
  }

  getItemOfSupplierById(id:string):Observable<SupplierItemResponseDTO[]>{
    return this.backApi.getItemOfSupplierById(id);
  }

  getStockOrdersOfSupplierById(id:string):Observable<StockOrderResponseDTO[]>{
    return this.backApi.getStockOrdersOfSupplierById(id);
  }
}
