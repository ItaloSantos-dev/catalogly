import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  private backApi = inject(BackApi);

  getItemsOfCatalog() {
    return this.backApi.getItemsOfCatalog();
  }
}
