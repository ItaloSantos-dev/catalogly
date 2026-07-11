import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';
import { CategoryRespondeDTO } from '../../../types/category/category-response';
import { CouponResponseDTO } from '../../../types/coupon/coupon-response';
import { SupplierResponseDTO } from '../../../types/supplier/supplier-response';
import { OrderResponseDTO } from '../../../types/order/order-response';
import { CatalogPublicResponseDTO } from '../../../types/catalog/catalog-public-response';

@Injectable({
  providedIn: 'root',
})
export class CatalogService {
  private backApi = inject(BackApi);

  createCatalog(data:FormData):Observable<any>{
    return this.backApi.createCatalog(data);
  }

  getCategorysOfCatalogById(id:string):Observable<[CategoryRespondeDTO]>{
    return this.backApi.getCategorysOfCatalogById(id);
  }

  getCouponsOfCatalogById(id:string):Observable<CouponResponseDTO[]>{
    return this.backApi.getCouponsOfCatalogById(id);
  }

  getSuppliersOfCatalog():Observable<SupplierResponseDTO[]>{
    return this.backApi.getSuppliersOfCatalog();
  }

  getOrdersOfCatalog():Observable<OrderResponseDTO[]>{
    return this.backApi.getOrdersOfCatalog();
  }

  getCatalogPublicBySlug(slug:string):Observable<CatalogPublicResponseDTO>{
    return this.backApi.getCatalogPublicBySlug(slug);
  }
}
