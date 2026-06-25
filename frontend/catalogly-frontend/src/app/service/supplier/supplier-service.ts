import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';
import { SupplierResponseDTO } from '../../../types/supplier/supplier-response';

@Injectable({
  providedIn: 'root',
})
export class SupplierService {
  private backApi = inject(BackApi);

  getSuppliersOfCatalog():Observable<SupplierResponseDTO[]>{
    return this.backApi.getSuppliersOfCatalog();
  }
}
