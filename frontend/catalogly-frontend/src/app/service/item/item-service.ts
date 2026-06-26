import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';
import { ItemResponseDTO } from '../../../types/item/item-response';

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
}
