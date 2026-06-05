import { Component, inject, signal } from '@angular/core';
import { SellerService } from '../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../types/catalog/catalog-private-response';

@Component({
  selector: 'app-catalog-side-menu',
  imports: [],
  templateUrl: './catalog-side-menu.html',
  styleUrl: './catalog-side-menu.css',
})
export class CatalogSideMenu {
  private sellerService = inject(SellerService);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO | null>null);
  sellerHasCatalog = signal(false);

  ngOnInit(){
    this.sellerService.catalogPrivateData$.subscribe(data =>{
      if(data){
        this.catalogPrivateOfSeller.set(data);
        this.sellerHasCatalog.set(true);
      }
    })
  }
}
