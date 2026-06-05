import { inject, Injectable } from '@angular/core';
import { CreateSellerRequestDTO } from '../../../types/seller/create-seller-request';
import { BehaviorSubject, Observable } from 'rxjs';
import { BackApi } from '../../api/back-api';
import { SellerResponseDTO } from '../../../types/seller/seller-response';
import { CatalogPrivateResponseDTO } from '../../../types/catalog/catalog-private-response';

@Injectable({
  providedIn: 'root',
})
export class SellerService {
  private backApi = inject(BackApi);

  private catalogPrivateData = new BehaviorSubject<CatalogPrivateResponseDTO | null>(null);

  catalogPrivateData$ = this.catalogPrivateData.asObservable();

  setCatalogPrivateData(data: CatalogPrivateResponseDTO) {
    this.catalogPrivateData.next(data);
  }

  registerSeller(data: CreateSellerRequestDTO): Observable<SellerResponseDTO> {
    return this.backApi.registerSeller(data);
  }
  getMyCatalog():Observable<CatalogPrivateResponseDTO>{
    return this.backApi.getMyCatalog();
  }
}
