import { DecimalPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { ItemService } from '../../../../../service/item/item-service';
import { ItemResponseDTO } from '../../../../../../types/item/item-response';
import { HelperService } from '../../../../../service/helper/helper-service';

@Component({
  selector: 'app-seller-show-items',
  imports: [DecimalPipe],
  templateUrl: './seller-show-items.html',
  styleUrl: './seller-show-items.css',
})
export class SellerShowItems {
  private ItemService = inject(ItemService);
  items = signal(<ItemResponseDTO[]>[]);
  private helperService = inject(HelperService);

  ngOnInit() {
    this.ItemService.getItemsOfCatalog().subscribe({
      next:(data) =>{
        this.items.set(data);
      },
      error:(err) =>{
        console.error("Error fetching items of catalog: ", err);
      }
    });

    this.helperService.setAtualPage(1);
  }
}
