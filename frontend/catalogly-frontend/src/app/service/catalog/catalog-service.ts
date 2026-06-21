import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';
import { CategoryRespondeDTO } from '../../../types/category/category-response';

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
}
