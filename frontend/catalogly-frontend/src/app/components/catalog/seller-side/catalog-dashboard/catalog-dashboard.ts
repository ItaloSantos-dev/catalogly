import { Component, inject, signal } from '@angular/core';
import { SellerService } from '../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../types/catalog/catalog-private-response';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-catalog-dashboard',
  imports: [DecimalPipe],
  templateUrl: './catalog-dashboard.html',
  styleUrl: './catalog-dashboard.css',
})
export class CatalogDashboard {
  private sellerService = inject(SellerService);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO | null>null);
  sellerHasCatalog = signal(false);
  
    ngOnInit(){
      this.sellerService.catalogPrivateData$.subscribe(data =>{
        if(data){
          console.log(data);
          (data);
          this.catalogPrivateOfSeller.set(data);
          this.sellerHasCatalog.set(true);
        }
      })
    }
}
