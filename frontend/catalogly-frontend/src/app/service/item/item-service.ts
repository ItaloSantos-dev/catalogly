import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';
import { ItemResponseDTO } from '../../../types/item/item-response';
import { UpdateItemRequestDTO } from '../../../types/item/update-item-request';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  private backApi = inject(BackApi);

  getItemsOfCatalog() {
    return this.backApi.getItemsOfCatalog();
  }

  createItem(data:FormData){
    return this.backApi.createItem(data);
  }

  getItemById(id:string):Observable<ItemResponseDTO>{
    return this.backApi.getItemById(id);
  }

  updateItemById(id:string, data:FormData):Observable<ItemResponseDTO>{
    return this.backApi.updateItemById(id, data);
  }
}
