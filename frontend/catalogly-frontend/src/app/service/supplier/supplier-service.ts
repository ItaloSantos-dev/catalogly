import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';
import { SupplierResponseDTO } from '../../../types/supplier/supplier-response';
import { CreateSupplierRequestDTO } from '../../../types/supplier/create-supplier-request';

@Injectable({
  providedIn: 'root',
})
export class SupplierService {
  private backApi = inject(BackApi);

  createSupplier(data:CreateSupplierRequestDTO):Observable<any>{
    return this.backApi.createSupplier(data);
  }
}
