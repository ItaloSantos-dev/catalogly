import { Component, inject, signal } from '@angular/core';
import { SellerService } from '../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../types/catalog/catalog-private-response';
import { DecimalPipe } from '@angular/common';
import { HelperService } from '../../../../service/helper/helper-service';

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
  private helperService = inject(HelperService);

  ngOnInit(){
    this.sellerService.catalogPrivateData$.subscribe(data =>{
      if(data){
        console.log(data);
        (data);
        this.catalogPrivateOfSeller.set(data);
        this.sellerHasCatalog.set(true);
      }
    })

    this.helperService.setAtualPage(0);

  }
}
