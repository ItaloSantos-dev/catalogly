import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { CreateCategoryRequestDTO } from '../../../types/category/create-category-request';
import { Observable } from 'rxjs';
import { ItemResponseDTO } from '../../../types/item/item-response';
import { CategoryRespondeDTO } from '../../../types/category/category-response';
import { UpdateCategoryrequestDTO } from '../../../types/category/update-category-request';
import { CatalogPublicResponseDTO } from '../../../types/catalog/catalog-public-response';

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

  getCategoryById(id:string):Observable<CategoryRespondeDTO>{
    return this.backApi.getCategoryByid(id);
  }

  updateCategoryById(id:string, data:UpdateCategoryrequestDTO):Observable<any>{
    return this.backApi.updateCategoryById(id, data);
  }

  
}
