import { inject, Injectable } from '@angular/core';
import { CreateSellerRequestDTO } from '../../../types/seller/create-seller-request';
import { Observable } from 'rxjs';
import { BackApi } from '../../api/back-api';
import { SellerResponseDTO } from '../../../types/seller/seller-response';

@Injectable({
  providedIn: 'root',
})
export class SellerService {
  private backApi = inject(BackApi);

  registerSeller(data: CreateSellerRequestDTO): Observable<SellerResponseDTO> {
    return this.backApi.registerSeller(data);
  }
}
