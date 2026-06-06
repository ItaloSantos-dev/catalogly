import { inject, Injectable } from '@angular/core';
import { CreateSellerRequestDTO } from '../../../types/seller/create-seller-request';
import { BehaviorSubject, Observable } from 'rxjs';
import { BackApi } from '../../api/back-api';
import { SellerResponseDTO } from '../../../types/seller/seller-response';
import { CatalogPrivateResponseDTO } from '../../../types/catalog/catalog-private-response';
import { LoginRequestDTO } from '../../../types/auth/login-request';

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

  loginSeller(data:LoginRequestDTO): Observable<string> {
    return this.backApi.login(data);
  }
  registerSeller(data: CreateSellerRequestDTO): Observable<SellerResponseDTO> {
    return this.backApi.registerSeller(data);
  }
  getMyCatalog():Observable<CatalogPrivateResponseDTO>{
    return this.backApi.getMyCatalog();
  }
}
