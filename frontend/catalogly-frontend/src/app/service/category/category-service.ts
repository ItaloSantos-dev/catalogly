import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { CreateCategoryRequestDTO } from '../../../types/category/create-category-request';
import { Observable } from 'rxjs';

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
}
