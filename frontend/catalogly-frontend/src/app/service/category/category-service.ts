import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { CreateCategoryRequestDTO } from '../../../types/category/create-category-request';
import { Observable } from 'rxjs';
import { ItemResponseDTO } from '../../../types/item/item-response';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private backApi = inject(BackApi)

  createCategory(data:CreateCategoryRequestDTO):Observable<any>{
    return this.backApi.createCategory(data);
  }

  deleteCategoryById(id:string):Observable<void>{
    return this.backApi.deleteCategoryById(id);
  }

  getItensOfCategoryById(id:string):Observable<ItemResponseDTO[]>{
    return this.backApi.getItensOfCategoryById(id);
  }
}
