import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CatalogService {
  private backApi = inject(BackApi);

  createCatalog(data:FormData):Observable<any>{
    return this.backApi.createCatalog(data);
  }
}
