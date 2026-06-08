import { Component, inject, signal } from '@angular/core';
import { ItemService } from '../../../../service/item/item-service';
import { ItemResponseDTO } from '../../../../../types/item/item-response';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-catalog-seller-side-item',
  imports: [DecimalPipe],
  templateUrl: './catalog-seller-side-item.html',
  styleUrl: './catalog-seller-side-item.css',
})
export class CatalogSellerSideItem {
  private ItemService = inject(ItemService);
  items = signal(<ItemResponseDTO[]>[]);

  ngOnInit() {
    this.ItemService.getItemsOfCatalog().subscribe({
      next:(data) =>{
        this.items.set(data);
      },
      error:(err) =>{
        console.error("Error fetching items of catalog: ", err);
      }
    });
  }
}
