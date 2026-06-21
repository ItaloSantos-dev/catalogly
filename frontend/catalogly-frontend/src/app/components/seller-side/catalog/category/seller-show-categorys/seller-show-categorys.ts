import { Component, inject, signal } from '@angular/core';
import { CatalogService } from '../../../../../service/catalog/catalog-service';
import { CategoryRespondeDTO } from '../../../../../../types/category/category-response';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { SellerService } from '../../../../../service/seller/seller-service';
import { HelperService } from '../../../../../service/helper/helper-service';

@Component({
  selector: 'app-seller-show-categorys',
  imports: [],
  templateUrl: './seller-show-categorys.html',
  styleUrl: './seller-show-categorys.css',
})
export class SellerShowCategorys {
  private catalogService = inject(CatalogService);
  private sellerService = inject(SellerService);
  categorysOfCatalog = signal(<CategoryRespondeDTO[]>[]);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO>{});
  private helperService = inject(HelperService);

  
  ngOnInit(){
    this.sellerService.getMyCatalog().subscribe((data) =>{
      if (data) {
        this.catalogPrivateOfSeller.set(data);

        this.catalogService.getCategorysOfCatalogById(this.catalogPrivateOfSeller().id).subscribe({
          next:(categories) =>{
            this.categorysOfCatalog.set(categories);
          },
          error:(err) =>{
            console.error("Error fetching categorys of catalog: ", err);
          }
        })

      }
    })


    this.helperService.setAtualPage(1);

  }
}
